package com.sorenkai.web.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sorenkai.web.BlockquoteCardStyle
import com.sorenkai.web.ButtonStyle
import com.sorenkai.web.auth.Auth
import com.sorenkai.web.auth.AuthState
import com.sorenkai.web.auth.IAuthProvider
import com.sorenkai.web.components.data.model.community.discussions.DiscussionOrder
import com.sorenkai.web.components.data.model.community.discussions.Kind
import com.sorenkai.web.components.widgets.BlockQuote
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.PostComposer
import com.sorenkai.web.components.widgets.cards.DiscussionItems
import com.sorenkai.web.components.widgets.header.SectionHeader
import com.sorenkai.web.en.data.CommunityDataEn
import com.sorenkai.web.es.data.CommunityDataEs
import com.sorenkai.web.toSitePalette
import com.sorenkai.web.ui.viewmodels.DiscussionsViewModel
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.visibility
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.times
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.koin.compose.koinInject

@Composable
fun CommunityContent(
    lang: String,
    breakpoint: Breakpoint,
) {
    val viewModel = koinInject<DiscussionsViewModel>()
    val auth = koinInject<IAuthProvider>()
    var token: String? = null
    val userId by auth.userId.collectAsState()

    LaunchedEffect(lang) {
        viewModel.fetchDiscussions(lang = lang, order = DiscussionOrder.NEWEST)
        viewModel.startPolling(lang = lang)
        token = auth.getAccessToken()
    }

    val discussions by viewModel.discussions.collectAsState()
    val pendingMap by viewModel.pendingComments.collectAsState()
    val rootPending = pendingMap["root"] ?: emptyList()

    val data =
        when (lang) {
            "es" -> CommunityDataEs
            else -> CommunityDataEn
        }
    val size = if (breakpoint <= Breakpoint.SM) {
        1.25.cssRem
    } else  if (breakpoint <= Breakpoint.MD) {
        1.35.cssRem
    } else {
        1.5.cssRem
    }
    val isAuthenticated by Auth.authState.collectAsState()

    Row(
        modifier = BlockquoteCardStyle.toModifier()
            .fillMaxWidth(),
    ) {
        BlockQuote(
            { Text(data.communityQuote) },
            modifier = Modifier
                .fontFamily("Playfair Display", "serif")
                .fontSize(size*0.9),
            citation = data.communityQuoteAuthor,
        )
    }

    LeadParagraph(
        breakpoint,
        modifier = Modifier.fontSize(size)
    ) { Text(data.communityLeadParagraph) }


    SectionHeader(
        data.policyHeading,
        breakpoint,
        modifier = Modifier.fontSize(size)
    )

    Row(
        modifier = Modifier
            .padding(topBottom = 2.cssRem)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Link(
            path = "policies/community",
            text = data.guidelinesLink
        )
        Link(
            path = "policies/terms",
            text = data.tosLink
        )
        Link(
            path = "policies/privacy",
            text = data.privacyLink
        )
    }

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (isAuthenticated != AuthState.Authenticated) {
            Button(
                attrs = ButtonStyle.toModifier()
                    .toAttrs{
                    onClick{
                        auth.login(window.location.pathname)
                    }
                }
            ) {
                Text(data.loginButton)
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .position(Position.Sticky)
                    .backgroundColor(ColorMode.current.toSitePalette().background)
                    .styleModifier {
                        property("top", "var(--nav-header-height)")
                    }
                    .zIndex(4)
                    .margin(bottom = 1.cssRem)
                    .padding(topBottom = 1.cssRem)
            ){
                PostComposer(
                    modifier = Modifier.id("new-post-composer"),
                    lang = lang,
                    placeholder = "What's on your mind?",
                    enabled = auth.authState.value == AuthState.Authenticated,
                    onSubmit = { body ->
                        console.log("New post submitted: $body")

                        viewModel.createDiscussion(
                            body = body,
                            lang = lang,
                            kind = Kind.POST
                        )
                    },
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 2.cssRem,
                    bottom = 4.cssRem
                ),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .cursor(Cursor.Pointer)
                    .visibility( if(rootPending.isNotEmpty()) Visibility.Visible else Visibility.Hidden),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                SpanText(
                    "Load New Posts",
                    modifier = Modifier
                        .fillMaxWidth(25.percent)
                        .backgroundColor(ColorMode.current.toSitePalette().nearBackground)
                        .textAlign(TextAlign.Center)
                        .borderRadius(0.5.cssRem)
                        .fontSize(1.2.cssRem)
                        .fontWeight(FontWeight.SemiBold)
                        .onClick { viewModel.showPending(null) }
                )
            }
            discussions.filter { it.parentId == null }.forEach { discussion ->
                DiscussionItems(
                    lang = lang,
                    discussion = discussion,
                    allDiscussions = discussions,
                    isAuthenticated = auth.authState.value == AuthState.Authenticated,
                    viewModel = viewModel,
                    userId = userId,
                    onAuthRequired = {
                        auth.login(window.location.pathname)
                    }
                )
            }
        }
    }
}
