package com.sorenkai.web.ui.viewmodels

import com.sorenkai.web.api.dto.discussions.DiscussionCreateDto
import com.sorenkai.web.api.dto.discussions.mapper.DiscussionPresentationMapper
import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.components.data.model.community.discussions.Discussion
import com.sorenkai.web.components.data.model.community.discussions.DiscussionOrder
import com.sorenkai.web.components.data.model.community.discussions.Kind
import com.sorenkai.web.components.data.model.report.Report
import com.sorenkai.web.components.data.model.report.ReportStatus
import com.sorenkai.web.components.data.model.report.TargetType
import com.sorenkai.web.repository.interfaces.IDiscussionRepository
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.sorenkai.web.auth.AuthState

@OptIn(ExperimentalTime::class)
class DiscussionsViewModelTest {
    @Test
    fun testAll() = runTest {
        println("[DEBUG_LOG] Starting testAll")
        
        val repo = MockDiscussionRepository()
        val auth = MockAuthProvider()
        val mapper = DiscussionPresentationMapper()
        
        // Populate repo BEFORE ViewModel creation so StateFlow has initial value
        val rootPost = Discussion(
            id = "root1",
            kind = Kind.POST,
            lang = "en",
            userId = "user1",
            body = "Root Post",
            createdAt = Instant.fromEpochMilliseconds(0)
        )
        repo.emit(listOf(rootPost))

        val viewModel = DiscussionsViewModel(repo, auth, mapper)
        
        // Trigger manual refresh to ensure data is picked up if init's onEach is too slow
        viewModel.fetchDiscussions("en", order = DiscussionOrder.NEWEST)
        advanceUntilIdle()

        println("[DEBUG_LOG] Starting Tests")

        // Test 1: Root posts load and poll correctly
        println("[DEBUG_LOG] Test 1: Root posts load")
        
        assert(viewModel.discussions.value.any { it.id == "root1" }) { "Root post should be visible. Current: ${viewModel.discussions.value}" }
        println("[DEBUG_LOG] Test 1 Passed")

        // Test 2: Auto-expansion stops exactly at siblings
        println("[DEBUG_LOG] Test 2: Auto-expansion stops at siblings")
        val child1 = Discussion(
            id = "child1",
            kind = Kind.COMMENT,
            lang = "en",
            parentId = "root1",
            userId = "user2",
            body = "Child 1",
            childCount = 1, // Let's make it have children to allow auto-expansion if it was single child
            createdAt = Instant.fromEpochMilliseconds(1)
        )
        
        // Expand root1
        viewModel.toggleExpanded("root1")
        advanceUntilIdle()
        repo.emit(listOf(rootPost, child1))
        advanceUntilIdle()

        val sibling1 = Discussion(
            id = "sib1",
            kind = Kind.COMMENT,
            lang = "en",
            parentId = "child1",
            userId = "user3",
            body = "Sibling 1",
            createdAt = Instant.fromEpochMilliseconds(2)
        )
        val sibling2 = Discussion(
            id = "sib2",
            kind = Kind.COMMENT,
            lang = "en",
            parentId = "child1",
            userId = "user4",
            body = "Sibling 2",
            createdAt = Instant.fromEpochMilliseconds(3)
        )

        // Emit siblings for child1.
        repo.emit(listOf(rootPost, child1, sibling1, sibling2))
        advanceUntilIdle()

        assert(viewModel.isExpanded("root1")) { "Root should be expanded" }
        assert(viewModel.isExpanded("child1")) { "Child1 should be auto-expanded because it was single child" }
        assert(!viewModel.isExpanded("sib1")) { "Sibling 1 should NOT be expanded" }
        assert(!viewModel.isExpanded("sib2")) { "Sibling 2 should NOT be expanded" }
        println("[DEBUG_LOG] Test 2 Passed")

        // Test 3: Data from a poll is diverted to pending
        println("[DEBUG_LOG] Test 3: Polling data diverted to pending")
        val pollNew = Discussion(
            id = "poll1",
            kind = Kind.COMMENT,
            lang = "en",
            parentId = "root1",
            userId = "user5",
            body = "Poll New",
            createdAt = Instant.fromEpochMilliseconds(4)
        )
        // root1 is in activePollIds but NOT in expansionInProgress anymore (fetchChildren finished)
        repo.emit(listOf(rootPost, child1, sibling1, sibling2, pollNew))
        advanceUntilIdle()

        assert(viewModel.discussions.value.none { it.id == "poll1" }) { "Polled item should NOT be in main list" }
        assert(viewModel.pendingComments.value["root1"]?.any { it.id == "poll1" } == true) { "Polled item should be in pending" }
        println("[DEBUG_LOG] Test 3 Passed")

        // Test 4: Author feedback bypasses pending
        println("[DEBUG_LOG] Test 4: Author feedback bypasses pending")
        auth.setUserId("me")
        val myPost = Discussion(
            id = "my1",
            kind = Kind.COMMENT,
            lang = "en",
            parentId = "root1",
            userId = "me",
            body = "My Post",
            createdAt = Instant.fromEpochMilliseconds(5)
        )
        repo.emit(listOf(rootPost, child1, sibling1, sibling2, pollNew, myPost))
        advanceUntilIdle()

        assert(viewModel.discussions.value.any { it.id == "my1" }) { "Author post should bypass pending" }
        println("[DEBUG_LOG] Test 4 Passed")

        // Test 5: Auto-expansion on creation
        println("[DEBUG_LOG] Test 5: Auto-expansion on creation")
        auth.setUserId("me")
        // root1 is expanded, but let's test a non-expanded one
        val root2 = Discussion(
            id = "root2",
            kind = Kind.POST,
            lang = "en",
            userId = "user1",
            body = "Root 2",
            createdAt = Instant.fromEpochMilliseconds(10)
        )
        repo.emit(repo.discussions.value + root2)
        advanceUntilIdle()
        
        assert(!viewModel.isExpanded("root2")) { "Root 2 should NOT be expanded yet" }
        
        viewModel.createDiscussion(
            body = "My Comment on Root 2",
            lang = "en",
            parentId = "root2",
            kind = Kind.COMMENT
        )
        advanceUntilIdle()
        
        assert(viewModel.isExpanded("root2")) { "Root 2 should be expanded after creating a comment on it" }
        assert(viewModel.discussions.value.any { it.body == "My Comment on Root 2" }) { "New comment should be visible immediately" }
        println("[DEBUG_LOG] Test 5 Passed")

        // Test 6: Child count synchronization and increment
        println("[DEBUG_LOG] Test 6: Child count synchronization")
        val root3 = Discussion(
            id = "root3",
            kind = Kind.POST,
            lang = "en",
            userId = "user1",
            body = "Root 3",
            childCount = 5,
            createdAt = Instant.fromEpochMilliseconds(20)
        )
        repo.emit(repo.discussions.value + root3)
        advanceUntilIdle()
        
        assert(viewModel.childCount.value["root3"] == 5) { "Child count for root3 should be 5. Current: ${viewModel.childCount.value["root3"]}" }
        
        println("[DEBUG_LOG] Test 6: Child count optimistic increment")
        viewModel.createDiscussion(
            body = "Comment on root3",
            lang = "en",
            parentId = "root3",
            kind = Kind.COMMENT
        )
        advanceUntilIdle()
        
        assert(viewModel.childCount.value["root3"] == 6) { "Child count for root3 should be 6 after creation. Current: ${viewModel.childCount.value["root3"]}" }
        println("[DEBUG_LOG] Test 6 Passed")

        // Test 7: Prevent child count downgrade from stale repository data
        println("[DEBUG_LOG] Test 7: Prevent child count downgrade")
        // root3 currently has childCount 6 in _childCount map (optimistically incremented)
        // Simulate a stale repository emission where root3 still has childCount 5
        val staleRoot3 = Discussion(
            id = "root3",
            kind = Kind.POST,
            lang = "en",
            userId = "user1",
            body = "Root 3",
            childCount = 5, // Stale value
            createdAt = Instant.fromEpochMilliseconds(20)
        )
        repo.emit(repo.discussions.value.map { if (it.id == "root3") staleRoot3 else it })
        advanceUntilIdle()

        assert(viewModel.childCount.value["root3"] == 6) { 
            "Child count for root3 should remain 6 even if repo returns stale 5. Current: ${viewModel.childCount.value["root3"]}" 
        }
        
        // Simulate repository catching up
        val freshRoot3 = staleRoot3.copy(childCount = 6)
        repo.emit(repo.discussions.value.map { if (it.id == "root3") freshRoot3 else it })
        advanceUntilIdle()
        assert(viewModel.childCount.value["root3"] == 6) { "Child count for root3 should still be 6. Current: ${viewModel.childCount.value["root3"]}" }

        // Simulate further growth
        val newerRoot3 = staleRoot3.copy(childCount = 7)
        repo.emit(repo.discussions.value.map { if (it.id == "root3") newerRoot3 else it })
        advanceUntilIdle()
        assert(viewModel.childCount.value["root3"] == 7) { "Child count for root3 should increase to 7. Current: ${viewModel.childCount.value["root3"]}" }
        
        println("[DEBUG_LOG] Test 7 Passed")
        
        // Test 8: Reporting sets isReportedByMe locally
        println("[DEBUG_LOG] Test 8: Reporting sets isReportedByMe locally")
        val reportId = "root1"
        assert(repo.discussions.value.find { it.id == reportId }?.isReportedByMe == false) { "Initially not reported" }
        viewModel.reportDiscussion(Kind.POST, reportId)
        advanceUntilIdle()
        assert(repo.discussions.value.find { it.id == reportId }?.isReportedByMe == true) { "Should be marked as reported locally" }
        println("[DEBUG_LOG] Test 8 Passed")

        println("[DEBUG_LOG] All tests finished successfully")
        println("[DEBUG_LOG] testAll finished")
    }

    private fun assert(condition: Boolean, message: () -> String) {
        if (!condition) {
            val msg = message()
            println("[DEBUG_LOG] Assertion Failed: $msg")
            throw AssertionError(msg)
        }
    }

    class MockDiscussionRepository : IDiscussionRepository {
        private val _discussions = MutableStateFlow<List<Discussion>>(emptyList())
        override val discussions: StateFlow<List<Discussion>> = _discussions.asStateFlow()

        fun emit(list: List<Discussion>) {
            _discussions.value = list
        }

        override suspend fun fetchDiscussions(
            lang: String,
            writingId: String?,
            parentId: String?,
            pageSize: Int?,
            cursor: String?,
            order: DiscussionOrder?,
            append: Boolean
        ) {
            // No-op for tests, data is pushed via emit()
        }

        override suspend fun createDiscussion(body: String, lang: String, dto: DiscussionCreateDto): Discussion {
            val d = Discussion(
                id = "new_" + js("Date.now()"),
                kind = dto.kind,
                lang = dto.lang,
                userId = dto.userId,
                body = dto.body,
                parentId = dto.parentId,
                writingId = dto.writingId,
                createdAt = Instant.fromEpochMilliseconds(js("Date.now()").unsafeCast<Double>().toLong())
            )
            _discussions.value += d
            return d
        }

        override suspend fun deleteDiscussion(discussionId: String): Discussion = throw NotImplementedError()
        override suspend fun restoreDiscussion(discussionId: String): Discussion = throw NotImplementedError()
        override suspend fun reportDiscussion(targetType: String, discussionId: String): Report {
            _discussions.value = _discussions.value.map {
                if (it.id == discussionId) it.copy(isReportedByMe = true) else it
            }
            return Report(
                id = "report-1",
                targetType = TargetType.valueOf(targetType),
                targetId = discussionId,
                reason = "Reported by user",
                createdAt = Instant.DISTANT_PAST,
                createdBy = "user-1",
                status = ReportStatus.OPEN
            )
        }
        override suspend fun moderateDiscussion(discussionId: String, dto: com.sorenkai.web.api.dto.discussions.DiscussionModerationDto): Discussion = throw NotImplementedError()
        override suspend fun editDiscussion(discussionId: String, body: String): Discussion = throw NotImplementedError()
        override suspend fun likeDiscussion(discussionId: String): Discussion = throw NotImplementedError()
        override suspend fun unlikeDiscussion(discussionId: String): Discussion = throw NotImplementedError()
    }

    class MockAuthProvider : IAuthProvider {
        private val _userId = MutableStateFlow<String?>(null)
        override val userId: StateFlow<String?> = _userId.asStateFlow()

        private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
        override val authState: StateFlow<AuthState> = _authState.asStateFlow()

        fun setUserId(id: String?) {
            _userId.value = id
            _authState.value = if (id != null) AuthState.Authenticated else AuthState.Unauthenticated
        }

        override fun getUserId(): String? = _userId.value
        override fun getUsername(): String? = _userId.value
        override suspend fun getAccessToken(): String? = null
        override fun getRoles(): List<String> = emptyList()

        override fun login(returnUrl: String?) {}
        override fun logout(returnUrl: String?) {}
        override suspend fun ensureHydrated() {}
    }
}
