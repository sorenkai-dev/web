package com.sorenkai.web.ui.viewmodels

import com.sorenkai.web.api.dto.discussions.DiscussionCreateDto
import com.sorenkai.web.api.dto.discussions.DiscussionModerationDto
import com.sorenkai.web.api.dto.discussions.DiscussionReadDto
import com.sorenkai.web.api.dto.discussions.mapper.DiscussionPresentationMapper
import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.components.data.model.community.discussions.DiscussionOrder
import com.sorenkai.web.components.data.model.community.discussions.Kind
import com.sorenkai.web.repository.interfaces.IDiscussionRepository
import com.sorenkai.web.state.ActionResult
import com.sorenkai.web.state.discussions.DiscussionsUiState
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@OptIn(ExperimentalTime::class)
class DiscussionsViewModel(
    private val repository: IDiscussionRepository,
    private val auth: IAuthProvider,
    private val presentationMapper: DiscussionPresentationMapper
) {
    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    private val _discussions = MutableStateFlow<List<DiscussionReadDto>>(emptyList())
    val discussions: StateFlow<List<DiscussionReadDto>> = _discussions.asStateFlow()

    private val _childCount = MutableStateFlow<Map<String, Int>>(emptyMap())
    val childCount: StateFlow<Map<String, Int>> = _childCount.asStateFlow()

    private val _expandedIds = MutableStateFlow<Set<String>>(emptySet())
    val expandedIds: StateFlow<Set<String>> = _expandedIds.asStateFlow()

    private val _loadedParentIds = MutableStateFlow<Set<String>>(emptySet())
    val loadedParentIds: StateFlow<Set<String>> = _loadedParentIds.asStateFlow()

    private val _loadingParentIds = MutableStateFlow<Set<String>>(emptySet())
    val loadingParentIds: StateFlow<Set<String>> = _loadingParentIds.asStateFlow()

    private val _activePollIds = MutableStateFlow<Set<String?>>(emptySet())
    private val _expansionInProgress = MutableStateFlow<Set<String?>>(emptySet())
    private val _pendingComments = MutableStateFlow<Map<String, List<DiscussionReadDto>>>(emptyMap())
    val pendingComments: StateFlow<Map<String, List<DiscussionReadDto>>> = _pendingComments.asStateFlow()

    private var currentOrder: DiscussionOrder = DiscussionOrder.NEWEST
    private var currentLang: String? = null
    private var currentWritingId: String? = null
    private var currentParentId: String? = null

    private val _screenState = MutableStateFlow<DiscussionsUiState>(DiscussionsUiState.Loading)
    val screenState: StateFlow<DiscussionsUiState> = _screenState.asStateFlow()

    private val _actionResults = MutableSharedFlow<ActionResult>()
    val actionResults: SharedFlow<ActionResult> = _actionResults

    private val _highlightedId = MutableStateFlow<String?>(null)
    val highlightedId: StateFlow<String?> = _highlightedId

    fun clear() {
        job.cancel()
    }

    private var pollingJob: Job? = null

    fun startPolling(lang: String) {
        pollingJob?.cancel()
        pollingJob = viewModelScope.launch {
            while (isActive) {
                delay(30000) // 30-second interval

                // Refresh only the branches in the active registry
                _activePollIds.value.forEach { id ->
                    fetchChildren(id)
                }
            }
        }
    }

    init {
        repository.discussions
            .onEach { rawDiscussions ->
                val currentUserId = auth.getUserId()
                val roles = auth.getRoles()
                val isModerator = roles.any { it == "admin" || it == "moderator" }

                val mapped = presentationMapper.mapToReadDtos(rawDiscussions, currentUserId, isModerator)
                val existingIds = _discussions.value.map { it.id }.toSet()
                val brandNewItems = mapped.filter { !existingIds.contains(it.id) }

                // 1. SEPARATION LOGIC
                // This determines exactly what bypasses the "Load New Posts" button.
                val (toShowNow, toHideInPending) = brandNewItems.partition { item ->
                    // Rule: Instant feedback for author (Crucial for comments)
                    // MUST BE FIRST to prioritize user content
                    if (currentUserId != null && item.userId == currentUserId) return@partition true

                    val pId = item.parentId

                    // Rule: Base posts show immediately if not polling root
                    if (pId == null && !_activePollIds.value.contains(null)) return@partition true

                    // Rule: Initial Expansion Rule (Children of something we just opened)
                    if (_expansionInProgress.value.contains(pId)) return@partition true

                    false
                }

                // 2. UPDATE MAIN STATE
                // Get all items that were already on screen and update them (likes, edits)
                val updatesToExisting = mapped.filter { existingIds.contains(it.id) }

                // Start with a map of what is currently visible
                val currentMap = _discussions.value.associateBy { it.id }.toMutableMap()

                // Add/Update existing items AND add the "Show Now" items (Author posts/comments)
                (updatesToExisting + toShowNow).forEach { currentMap[it.id] = it }

                // 3. REBUILD LIST WITH HIERARCHY-AWARE SORTING
                val allItems = currentMap.values.toList()
                _discussions.value = if (currentOrder == DiscussionOrder.NEWEST) {
                    // Keep base posts at top, sort siblings newest-to-oldest
                    allItems.sortedWith(
                        compareByDescending<DiscussionReadDto> { it.parentId == null }
                            .thenByDescending { it.createdAt }
                    )
                } else {
                    // Oldest first
                    allItems.sortedBy { it.createdAt }
                }

                // 4. UPDATE PENDING MAP (Items hidden behind the "Load New Posts" button)
                if (toHideInPending.isNotEmpty()) {
                    val currentPending = _pendingComments.value.toMutableMap()
                    toHideInPending.forEach { item ->
                        val key = item.parentId ?: "root"
                        val list = currentPending.getOrPut(key) { emptyList() }
                        if (list.none { it.id == item.id }) {
                            currentPending[key] = list + item
                        }
                    }
                    _pendingComments.value = currentPending
                }

                // 5. CLEANUP & AUTO-EXPANSION
                if (brandNewItems.isNotEmpty()) {
                    val parentIdsProcessed = brandNewItems.map { it.parentId }.toSet()
                    _expansionInProgress.value -= parentIdsProcessed
                }

                // 6. UPDATE CHILD COUNT MAP
                // Use childCount from the DTO as the primary source of truth, but don't downgrade
                // to stale values from the repository if we've optimistically incremented.
                _childCount.update { currentCounts ->
                    val newCounts = mapped.associate { it.id to it.childCount }
                    val merged = currentCounts.toMutableMap()
                    newCounts.forEach { (id, count) ->
                        val current = merged[id] ?: 0
                        merged[id] = maxOf(current, count)
                    }
                    merged
                }

                _screenState.value = DiscussionsUiState.Ready

                // Probe for single-child paths to auto-expand
                val newlyExpanded = mutableSetOf<String>()
                _expandedIds.value.forEach { parentId ->
                    val children = mapped.filter { it.parentId == parentId }
                    if (children.size == 1) {
                        val singleChild = children.first()
                        if (singleChild.childCount > 0 && !_expandedIds.value.contains(singleChild.id)) {
                            newlyExpanded.add(singleChild.id)
                        }
                    }
                }
                if (newlyExpanded.isNotEmpty()) {
                    _expandedIds.value += newlyExpanded
                    _activePollIds.value += newlyExpanded
                    _expansionInProgress.value += newlyExpanded
                    newlyExpanded.forEach { fetchChildren(it) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun fetchDiscussions(
        lang: String,
        writingId: String? = null,
        order: DiscussionOrder
    ) {
        viewModelScope.launch {
            if (order != currentOrder || lang != currentLang || writingId != currentWritingId) {
                _discussions.value = emptyList()
                _expandedIds.value = emptySet()
                _activePollIds.value = setOf(null) // Start polling for root
                _expansionInProgress.value = setOf(null) // First load for root
                _loadedParentIds.value = emptySet()
                _loadingParentIds.value = emptySet()
                _pendingComments.value = emptyMap()
                currentOrder = order
                currentLang = lang
                currentWritingId = writingId
            }
            _screenState.value = DiscussionsUiState.Loading
            try {
                repository.fetchDiscussions(
                    lang = lang,
                    writingId = writingId,
                    parentId = null,
                    order = order
                )
            } catch (e: Exception) {
                _screenState.value =
                    DiscussionsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleExpanded(id: String) {
        val isNowExpanded = !_expandedIds.value.contains(id)

        if (isNowExpanded) {
            _expandedIds.value += id
            _activePollIds.value += id // Add to polling registry
            _expansionInProgress.value += id // First load begins

            val discussion = _discussions.value.find { it.id == id }
            if (discussion != null && discussion.childCount > 0 && !_loadedParentIds.value.contains(id) && !_loadingParentIds.value.contains(id)) {
                fetchChildren(id)
            } else if (discussion != null && discussion.childCount > 0) {
                // Already loaded, so "first load" is effectively done immediately
                _expansionInProgress.value -= id
            }
        } else {
            _expandedIds.value -= id
            // Note: Per your rule, we DO NOT remove from _activePollIds here
            // so data is ready if they re-expand.
        }
    }

    fun isExpanded(id: String): Boolean = _expandedIds.value.contains(id)

    private fun expandAndProbe(id: String) {
        if (!_expandedIds.value.contains(id)) {
            _expandedIds.value += id
            _activePollIds.value += id
            _expansionInProgress.value += id // First load for auto-expanded branch

            val discussion = _discussions.value.find { it.id == id }
            if (discussion != null && discussion.childCount > 0) {
                if (!_loadedParentIds.value.contains(id) && !_loadingParentIds.value.contains(id)) {
                    fetchChildren(id)
                } else {
                    // Probe deeper if data already exists
                    val children = _discussions.value.filter { it.parentId == id }
                    if (children.size == 1) {
                        expandAndProbe(children.first().id)
                    }
                }
            }
        }
    }

    private fun fetchChildren(parentId: String?) {
        val lang = currentLang ?: return
        viewModelScope.launch {
            if (parentId != null && _loadingParentIds.value.contains(parentId)) return@launch
            if (parentId != null) _loadingParentIds.value += parentId
            try {
                repository.fetchDiscussions(
                    lang = lang,
                    parentId = parentId,
                    order = currentOrder,
                    append = true
                )
                if (parentId != null) _loadedParentIds.value += parentId
            } catch (e: Exception) {
                // Log error or update UI state
            } finally {
                if (parentId != null) _loadingParentIds.value -= parentId
                // REMOVED: _expansionInProgress.value -= parentId
                // (The init block now handles this cleanup)
            }
        }
    }

    fun showPending(parentId: String?) {
        console.log("showPending: $parentId")
        console.log("pendingComments: ${_pendingComments.value}")
        console.log("currentOrder: $currentOrder")
        val key = parentId ?: "root"
        val items = _pendingComments.value[key] ?: return

        // Move from pending to active
        _discussions.value =
            if (parentId == null && currentOrder == DiscussionOrder.NEWEST) {
                // Root + NEWEST → prepend
                (items + _discussions.value).distinctBy { it.id }
            } else {
                // Replies or OLDEST → append (existing behavior)
                (_discussions.value + items).distinctBy { it.id }
            }

        // Remove from pending
        val newPending = _pendingComments.value.toMutableMap()
        newPending.remove(key)
        _pendingComments.value = newPending
    }


    // Proxy methods for repository actions
    fun createDiscussion(
        body: String,
        lang: String,
        writingId: String? = null,
        parentId: String? = null,
        kind: Kind
    ) {
        viewModelScope.launch {
            runCatching {
                console.log("attempting to create discussion: $body")
                val currentUserId = auth.getUserId() ?: throw IllegalStateException("Must be authenticated")
                repository.createDiscussion(
                    body,
                    lang,
                    DiscussionCreateDto(
                        kind = kind,
                        body = body,
                        lang = lang,
                        userId = currentUserId,
                        writingId = writingId,
                        parentId = parentId
                    )
                )
            }.onSuccess {
                console.log("discussion created successfully")
                _actionResults.emit(ActionResult.Success)
                parentId?.let { pId ->
                    _childCount.update { counts ->
                        val currentCount = counts[pId] ?: 0
                        counts + (pId to (currentCount + 1))
                    }
                    _expandedIds.value += pId
                    _activePollIds.value += pId
                    fetchChildren(pId)
                } ?: fetchDiscussions(lang, order = DiscussionOrder.NEWEST)
            }.onFailure {
                console.log("DiscussionViewmodel createDiscussion onFailure: ${it.message}")
                _actionResults.emit(
                    ActionResult.Failure(
                        it.message ?: "Failed to create"
                    )
                )
            }
        }
    }

    fun deleteDiscussion(id: String) {
        viewModelScope.launch {
            runCatching {
                repository.deleteDiscussion(id)
            }.onSuccess {
                _actionResults.emit(ActionResult.Success)
            }.onFailure { e ->
                _actionResults.emit(
                    ActionResult.Failure(
                        e.message ?: "Failed to delete discussion"
                    )
                )
            }
        }
    }

    fun restoreDiscussion(id: String) {
        viewModelScope.launch {
            runCatching {
                repository.restoreDiscussion(id)
            }.onSuccess {
                _actionResults.emit(ActionResult.Success)
            }.onFailure { e ->
                _actionResults.emit(
                    ActionResult.Failure(
                        e.message ?: "Failed to restore discussion"
                    )
                )
            }
        }
    }

    fun reportDiscussion(id: String) {
        viewModelScope.launch {
            runCatching {
                repository.reportDiscussion(id)
            }.onSuccess {
                _actionResults.emit(ActionResult.Success)
            }.onFailure { e ->
                _actionResults.emit(
                    ActionResult.Failure(
                        e.message ?: "Failed to report discussion"
                    )
                )
            }
        }
    }

    fun likeDiscussion(id: String) {
        viewModelScope.launch {
            runCatching {
                repository.likeDiscussion(id)
            }.onSuccess {
                _actionResults.emit(ActionResult.Success)
            }.onFailure { e ->
                _actionResults.emit(
                    ActionResult.Failure(
                        e.message ?: "Failed to like discussion"
                    )
                )
            }
        }
    }

    fun unlikeDiscussion(id: String) {
        viewModelScope.launch {
            runCatching {
                repository.unlikeDiscussion(id)
            }.onSuccess {
                _actionResults.emit(ActionResult.Success)
            }.onFailure { e ->
                _actionResults.emit(
                    ActionResult.Failure(
                        e.message ?: "Failed to unlike discussion"
                    )
                )
            }
        }
    }

    fun moderateDiscussion(id: String, dto: DiscussionModerationDto) {
        viewModelScope.launch {
            runCatching {
                repository.moderateDiscussion(id, dto)
            }.onSuccess {
                _actionResults.emit(ActionResult.Success)
            }.onFailure { e ->
                _actionResults.emit(
                    ActionResult.Failure(
                        e.message ?: "Failed to moderate discussion"
                    )
                )
            }
        }
    }

    fun editDiscussion(id: String, body: String) {
        viewModelScope.launch {
            runCatching {
                repository.editDiscussion(id, body)
            }.onSuccess {
                _actionResults.emit(ActionResult.Success)
            }.onFailure { e ->
                _actionResults.emit(
                    ActionResult.Failure(
                        e.message ?: "Failed to edit discussion"
                    )
                )
            }
        }
    }

    fun toggleExpandedWithAutoProbing(id: String) {
        val currentlyExpanded = _expandedIds.value.contains(id)

        if (currentlyExpanded) {
            _expandedIds.value -= id
        } else {
            expandAndProbe(id)
        }
    }

    fun highlightComment(id: String) {
        _highlightedId.value = id
        viewModelScope.launch {
            delay(2500)
            if (_highlightedId.value == id) {
                _highlightedId.value = null
            }
        }
    }
}
