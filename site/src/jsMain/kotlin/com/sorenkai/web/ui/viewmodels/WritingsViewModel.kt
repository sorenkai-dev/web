package com.sorenkai.web.ui.viewmodels

import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.dto.discussions.DiscussionCreateDto
import com.sorenkai.web.api.dto.writings.WritingDetailResponse
import com.sorenkai.web.auth.AuthState
import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.components.data.model.community.discussions.Kind
import com.sorenkai.web.components.data.model.writing.WritingEntry
import com.sorenkai.web.repository.interfaces.IDiscussionRepository
import com.sorenkai.web.repository.interfaces.IWritingRepository
import com.sorenkai.web.state.ActionResult
import kotlin.js.Promise
import kotlin.time.Instant
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WritingsViewModel(
    private val writingsRepo: IWritingRepository,
    private val discussionRepo: IDiscussionRepository,
    private val auth: IAuthProvider
) {

    private val job = SupervisorJob()
    private val viewModelScope = CoroutineScope(job + Dispatchers.Main)

    /* ---------- State ---------- */

    private val _allWritings = MutableStateFlow<List<WritingEntry>>(emptyList())
    private val _visibleWritings = MutableStateFlow<List<WritingEntry>>(emptyList())
    val writings: StateFlow<List<WritingEntry>> = _visibleWritings.asStateFlow()

    private val _groupedTags = MutableStateFlow<Map<String, List<String>>>(emptyMap())
    val groupedTags: StateFlow<Map<String, List<String>>> = _groupedTags.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _selectedTag = MutableStateFlow<String?>(null)
    val selectedTag: StateFlow<String?> = _selectedTag.asStateFlow()

    private val _resolvedWritingId = MutableStateFlow<String?>(null)
    val resolvedWritingId: StateFlow<String?> = _resolvedWritingId.asStateFlow()

    private val _actionResults = MutableSharedFlow<ActionResult>()
    val actionResults: SharedFlow<ActionResult> = _actionResults

    private val _selectedWritingDetail = MutableStateFlow<WritingDetailResponse?>(null)
    val selectedWritingDetail: StateFlow<WritingDetailResponse?> = _selectedWritingDetail.asStateFlow()

    private val _authState = MutableStateFlow<AuthState?>(null)
    val authState: StateFlow<AuthState?> = _authState.asStateFlow()

    private var currentLang: String? = null

    /* ---------- Lifecycle ---------- */

    fun clear() {
        job.cancel()
    }

    /* ---------- Loading ---------- */

    fun loadWritingDetail(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            when (val res = writingsRepo.getWriting(id)) {
                is ApiResponse.Success -> _selectedWritingDetail.value = res.data
                is ApiResponse.HttpError -> _error.value = "HTTP ${res.code}"
                is ApiResponse.NetworkError -> _error.value = "Network error"
                is ApiResponse.UnknownError -> _error.value = "Unknown error"
            }
            _loading.value = false
        }
    }

    fun loadWritings(lang: String) {
        if (lang == currentLang && _allWritings.value.isNotEmpty()) return

        currentLang = lang

        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            when (val res = writingsRepo.getWritings(tag = null)) {
                is ApiResponse.Success -> {
                    _allWritings.value = res.data
                    recomputeVisible()
                }
                is ApiResponse.HttpError ->
                    _error.value = "HTTP ${res.code}"
                is ApiResponse.NetworkError ->
                    _error.value = "Network error"
                is ApiResponse.UnknownError ->
                    _error.value = "Unknown error"
            }

            _loading.value = false
        }
    }

    /* ---------- Filtering ---------- */

    fun selectCategory(category: String?) {
        _selectedCategory.value = if (category == "all") null else category
        _selectedTag.value = null
        recomputeVisible()
    }

    fun selectTag(tag: String) {
        _selectedTag.value = tag
        recomputeVisible()
    }

    private fun matchesCategory(
        writing: WritingEntry,
        category: String?
    ): Boolean {
        if (category == null) return true
        return writing.tags.any { it.startsWith("$category:") }
    }

    /* ---------- Sorting + Projection ---------- */

    private fun recomputeVisible() {
        val lang = currentLang ?: return
        val category = _selectedCategory.value
        val tag = _selectedTag.value

        val filtered =
            _allWritings.value
                .filter { it.language == lang }
                .filter { matchesCategory(it, category) }
                .filter { tag == null || it.tags.contains(tag) }

        val sorted =
            filtered.sortedWith(
                compareBy<WritingEntry> { !(it.featured ?: false) }
                    .thenByDescending { getLatestDate(it) }
                )

        _visibleWritings.value = sorted
        computeGroupedTags(sorted)
    }

    private fun computeGroupedTags(writings: List<WritingEntry>) {
        val tags =
            writings
                .flatMap { it.tags }
                .distinct()
                .sorted()

        _groupedTags.value =
            tags
                .groupBy { tag ->
                    when {
                        tag.startsWith("theme:") -> "theme"
                        tag.startsWith("topic:") -> "topic"
                        tag.startsWith("series:") -> "series"
                        tag.startsWith("concept:") -> "concept"
                        tag.startsWith("project:") -> "project"
                        else -> "other"
                    }
                }
                .mapValues { (_, list) -> list.sorted() }
    }

    /* ---------- Actions ---------- */

    fun recordView(id: String) {
        viewModelScope.launch {
            writingsRepo.incrementView(id)
        }
    }

    fun recordShare(id: String) {
        viewModelScope.launch {
            writingsRepo.incrementShare(id)
        }
    }

    fun recordSalesClick(id: String) {
        viewModelScope.launch {
            writingsRepo.incrementSalesClick(id)
        }
    }

    fun share(writing: WritingEntry, lang: String) {
        viewModelScope.launch {
            val origin = window.location.origin
            val langPath = if (lang == "en" || lang == "es") lang else "en"
            val url = "$origin/$langPath/writings/${writing.id}"

            var didShare = false

            try {
                val nav = window.navigator.asDynamic()
                if (nav.share != undefined) {
                    val data = js("({})")
                    data.title = writing.title
                    data.text = writing.synopsis
                    data.url = url
                    (nav.share(data) as Promise<dynamic>).await()
                    didShare = true
                } else {
                    val clip = nav.clipboard
                    if (clip != undefined && clip.writeText != undefined) {
                        (clip.writeText(url) as Promise<dynamic>).await()
                        window.alert("Link copied to clipboard")
                        didShare = true
                    } else {
                        window.prompt("Copy this link:", url)
                        didShare = true
                    }
                }
            } catch (t: Throwable) {
                console.error("Share failed", t)
            }

            if (didShare) {
                writingsRepo.incrementShare(writing.id)
                _actionResults.emit(ActionResult.Success)
            }
        }
    }

    /* ---------- Slug Resolution ---------- */

    fun resolveSlug(slug: String) {
        viewModelScope.launch {
            when (val res = writingsRepo.resolveSlug(slug)) {
                is ApiResponse.Success ->
                    _resolvedWritingId.value = res.data.id
                else ->
                    _resolvedWritingId.value = null
            }
        }
    }

    private fun updateWriting(
        id: String,
        transform: (WritingEntry) -> WritingEntry
    ) {
        _allWritings.value =
            _allWritings.value.map {
                if (it.id == id) transform(it) else it
            }

        recomputeVisible()
    }

    fun toggleLike(writingId: String) {
        val userId = auth.getUserId()

        if (userId == null) {
            viewModelScope.launch {
                _actionResults.emit(
                    ActionResult.Failure("Authentication required")
                )
            }
            return
        }
        val current = _allWritings.value.firstOrNull { it.id == writingId } ?: return
        val wasLiked = current.stats.isLikedByMe

        // optimistic update
        applyOptimisticLike(writingId)

        viewModelScope.launch {
            val result =
                runCatching {
                    if (wasLiked) {
                        writingsRepo.unlike(writingId)
                    } else {
                        writingsRepo.like(writingId)
                    }
                }

            if (result.isFailure) {
                rollbackLike(writingId)
            }
        }
    }

    private fun applyOptimisticLike(writingId: String) {
        val current = _allWritings.value.firstOrNull { it.id == writingId } ?: return
        val wasLiked = current.stats.isLikedByMe

        updateWriting(writingId) {
            it.copy(
                stats = it.stats.copy(
                    isLikedByMe = !wasLiked,
                    likesCount = (it.stats.likesCount + if (wasLiked) -1 else 1).coerceAtLeast(0)
                )
            )
        }
    }

    private fun rollbackLike(writingId: String) {
        val current = _allWritings.value.firstOrNull { it.id == writingId } ?: return
        val wasLiked = current.stats.isLikedByMe

        updateWriting(writingId) {
            it.copy(
                stats = it.stats.copy(
                    isLikedByMe = !wasLiked,
                    likesCount = it.stats.likesCount + if (wasLiked) -1 else 1
                )
            )
        }
    }

    fun createComment(
        body: String,
        lang: String,
        writingId: String
    ) {
        viewModelScope.launch {
            runCatching {
                val userId = auth.getUserId() ?: throw IllegalStateException("Must be authenticated")
                discussionRepo.createDiscussion(
                    body = body,
                    lang = lang,
                    DiscussionCreateDto(
                        kind = Kind.POST,
                        body = body,
                        lang = lang,
                        writingId = writingId,
                        userId = userId,
                        parentId = null
                    )
                )
            }.onSuccess {
                console.log("Discussion created successfully")
                _actionResults.emit(ActionResult.Success)
            }.onFailure {
                console.log("WritingDiscussionActionsViewModel createDiscussion onFailure: ${it.message}")
                _actionResults.emit(
                    ActionResult.Failure(
                        it.message ?: "Failed to create"
                    )
                )
            }
        }
    }

    private fun getLatestDate(entry: WritingEntry): Instant? {
        return listOfNotNull(entry.createdAt, entry.updatedAt, entry.publishedAt)
            .maxOrNull()
    }

    fun login() {
        auth.login()
    }

}
