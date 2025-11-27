package com.sorenkai.web.api.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.SpinnerStyle
import com.sorenkai.web.api.ApiClient
import com.sorenkai.web.api.ApiResponse
import com.sorenkai.web.api.dto.WritingListResponse
import com.sorenkai.web.components.data.model.WritingEntry
import com.sorenkai.web.components.util.Res
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.CategoryFilterBar
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.ModalOverlay
import com.sorenkai.web.components.widgets.WritingCard
import com.sorenkai.web.en.data.WritingDataEn
import com.sorenkai.web.en.widgets.ArticleModal
import com.sorenkai.web.es.data.WritingDataEs
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.url.URLSearchParams

private fun matchesCategory(writing: WritingEntry, category: String?): Boolean {
    if (category == null || category == "all") return true

    return writing.tags.any { tag ->
        tag.startsWith("$category:")
    }
}


@Composable
fun WritingContent(
    breakpoint: Breakpoint,
    lang: String,
    openSlugFromUrl: String? = null
) {
    val data =
        when (lang) {
            "es" -> WritingDataEs
            else -> WritingDataEn
        }
    var showModal by remember { mutableStateOf(false) }
    var modalTitle by remember { mutableStateOf("") }
    var modalSlug by remember { mutableStateOf("") }
    val openSlug = remember { mutableStateOf<String?>(null) }


    val closeModal = {
        showModal = false
        modalTitle = ""
        modalSlug = ""
    }

    Row(
        modifier = BlockquoteCardStyle.toModifier()
            .fillMaxWidth(),
    ) {
        BlockQuote(
            content = { Text(data.writingQuote) },
            citation = data.writingQuoteAuthor,
            modifier = Modifier.fontFamily("Playfair Display", "serif")
        )
    }
    LeadParagraph(breakpoint) { Text(data.leadParagraph) }

    var isLoading by remember { mutableStateOf(true) }
    var result by remember { mutableStateOf<ApiResponse<List<WritingEntry>>?>(null) }
    var selectedTag by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    CategoryFilterBar(
        breakpoint = breakpoint,
        activeCategory = selectedCategory,
        onSelect = { cat ->
            if (cat == null || cat == "all") {
                selectedCategory = null
                selectedTag = null
            } else {
                selectedCategory = cat
                selectedTag = null
            }
        },
        lang = lang
    )

    suspend fun toggleLike(writing: WritingEntry, liked: Boolean) {
        if (liked) {
            ApiClient.like(writing.slug)
        } else {
            ApiClient.unlike(writing.slug)
        }
    }

    // Wrap suspend functions in coroutine scope
    val scope = rememberCoroutineScope()

    fun handleViewsClick(writing: WritingEntry) {
        scope.launch { ApiClient.incrementView(writing.slug) }
    }

    fun handleSalesClick(writing: WritingEntry) {
        scope.launch{ ApiClient.incrementSalesClick(writing.slug) }
    }

    fun handleLikeToggle(writing: WritingEntry, liked: Boolean) {
        scope.launch { toggleLike(writing, liked) }
    }

    fun handleShare(writing: WritingEntry) {
        scope.launch {
            val origin = window.location.origin
            val langPath = when (lang) {
                "en", "es" -> lang
                else -> "en"
            }
            val url = "$origin/$langPath/writings/${writing.slug}"

            var didShare = false
            try {
                val nav = window.navigator.asDynamic()
                if (nav.share != undefined) {
                    val data = js("({})")
                    data.title = writing.title
                    data.text = writing.synopsis
                    data.url = url
                    // Await native share
                    (nav.share(data) as kotlin.js.Promise<dynamic>).await()
                    didShare = true
                } else {
                    val clip = nav.clipboard
                    if (clip != undefined && clip.writeText != undefined) {
                        (clip.writeText(url) as kotlin.js.Promise<dynamic>).await()
                        didShare = true
                        window.alert("Link copied to clipboard")
                    } else {
                        // Last resort fallback: prompt user to copy
                        window.prompt("Copy this link:", url)
                        didShare = true
                    }
                }
            } catch (t: Throwable) {
                console.error("Share failed", t)
                try {
                    window.prompt("Copy this link:", url)
                    didShare = true
                } catch (_: Throwable) {
                }
            }

            if (didShare) {
                ApiClient.incrementShare(writing.slug)
            }
        }
    }

    LaunchedEffect(openSlugFromUrl) {
        if (!openSlugFromUrl.isNullOrBlank()) {
            val json = Json { ignoreUnknownKeys = true }

            val articleRes = ApiClient.safeApiGet("/v1/writings/$openSlugFromUrl") { responseText ->
                json.decodeFromString<WritingEntry>(responseText)
            }

            if (articleRes is ApiResponse.Success) {
                val article = articleRes.data
                modalTitle = article.title
                modalSlug = article.slug
                showModal = true
            }
        }
    }

//    LaunchedEffect(Unit) {
//        val json = Json { ignoreUnknownKeys = true }
//        when (val tagRes = ApiClient.safeApiGet("/v1/tags") { responseText ->
//            json.decodeFromString<List<String>>(responseText)
//        }) {
//            is ApiResponse.Success -> tags = tagRes.data
//            else -> tags = emptyList()
//        }
//    }

    LaunchedEffect(selectedTag) {
        // Fetch writings whenever the selected tag changes
        isLoading = true
        val json = Json { ignoreUnknownKeys = true }
        val params = URLSearchParams().apply {
            if (!selectedTag.isNullOrBlank()) append("tag", selectedTag!!)
        }
        val qs = params.toString().let { if (it.isNotEmpty()) "?$it" else "" }
        result = ApiClient.safeApiGet("/v1/writings$qs") { responseText ->
            json.decodeFromString<WritingListResponse>(responseText).items
        }
        isLoading = false

        // AFTER writings load:
        openSlug.value?.let { slug ->
            if (!showModal) {  // prevent duplicate opening
                val match = (result as? ApiResponse.Success)
                    ?.data
                    ?.firstOrNull { it.slug == slug }

                if (match != null) {
                    modalTitle = match.title
                    modalSlug = match.slug
                    showModal = true
                }
            }
            openSlug.value = null
        }
    }

    when {
        isLoading -> {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 24.px),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                P {
                    Text(data.loadingText)
                }
                Image(
                    src = Res.Img.LOGO,
                    modifier = SpinnerStyle.toModifier()
                )
            }
        }
        else -> when (val res = result) {
            is ApiResponse.Success -> Column(
                Modifier
                    .fillMaxWidth()
                    .gap(4.cssRem)
            ) {
                val sortedWritings =
                    res.data
                        .filter { it.language == lang }
                        .filter { matchesCategory(it, selectedCategory) }
                        .map { it to (getLatestDate(it) ?: "") }
                        .sortedWith(
                            compareBy<Pair<WritingEntry, String>> { !(it.first.featured ?: false) }
                                .thenByDescending { it.second }
                        )
                        .map { it.first }
                val filteredTags =
                    sortedWritings
                        .flatMap { it.tags }
                        .distinct()
                        .sorted()
                val grouped = groupTags(filteredTags)
                sortedWritings.forEach { writing ->
                    WritingCard(
                        writing = writing,
                        breakpoint = breakpoint,
                        onReadArticle = { title, slug ->
                            modalTitle = title
                            modalSlug = slug
                            showModal = true
                        },
                        onTagClick = { fullTag ->
                            val category = fullTag.substringBefore(":")
                            selectedCategory = category
                            selectedTag = fullTag
                        },
                        onShareClick = ::handleShare,
                        onViewToggle = ::handleViewsClick,
                        onSalesClick = ::handleSalesClick,
                        lang = lang
                    )
                }
            }

            is ApiResponse.HttpError -> P { Text("${data.httpErrorText} ${res.code}") }
            is ApiResponse.NetworkError -> P { Text(data.networkErrorText) }
            is ApiResponse.UnknownError -> P { Text(data.unknownErrorText) }
            null -> P { Text(data.loadingText) }
        }
    }

    // 3. Conditional Modal Rendering
    if (showModal && modalSlug.isNotEmpty()) {
        ModalOverlay(
            title = modalTitle,
            onClose = closeModal // Use the helper function here
        ) {
            // ArticleModal now loads and renders the content inside the overlay
            ArticleModal(slug = modalSlug, lang)
        }
    }

}

private fun groupTags(tags: List<String>): Map<String, List<String>> {
    return tags
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

fun getLatestDate(entry: WritingEntry): String? {
    // Collect all date strings (non-null) and find the maximum one.
    return listOfNotNull(entry.createdAt, entry.updatedAt, entry.publishedAt)
        .maxOrNull()
}
