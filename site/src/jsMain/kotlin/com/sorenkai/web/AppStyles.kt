package com.sorenkai.web

import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.TransitionTimingFunction
import com.varabyte.kobweb.compose.css.functions.LinearGradient
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.forms.ButtonStyle
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import com.varabyte.kobweb.silk.components.layout.HorizontalDividerStyle
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariantBase
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.active
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.selectors.visited
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import com.varabyte.kobweb.silk.theme.modifyStyleBase
import org.jetbrains.compose.web.css.*

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) {
    // Smooth scrolling unless user prefers reduced motion
    ctx.stylesheet.registerStyle("html") {
        cssRule(CSSMediaQuery.MediaFeature("prefers-reduced-motion", StylePropertyValue("no-preference"))) {
            Modifier.scrollBehavior(ScrollBehavior.Smooth)
        }
        cssRule("") {
            Modifier
                .styleModifier {
                    property("color-scheme", "light dark")
                    property("forced-color-adjust", "none")
                }
        }
    }

    // Base body styles
    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily(
                "Nunito", // primary body font
                "Segoe UI", "Roboto", "Oxygen", "Ubuntu",
                "Cantarell", "Helvetica Neue", "sans-serif"
            )
            .fontSize(18.px)
            .lineHeight(1.6) // a bit more space for essay readability
    }

    // Full-width dividers
    ctx.theme.modifyStyleBase(HorizontalDividerStyle) {
        Modifier.fillMaxWidth()
    }
}

val PageContentStyle = CssStyle {
    base { Modifier.fillMaxSize().padding(leftRight = 2.cssRem, top = 4.cssRem) }
    Breakpoint.MD { Modifier.maxWidth(60.cssRem) }
}


// Lead / Intro paragraph
val LeadParagraphStyle = CssStyle {
    base {
        Modifier
            .fontFamily("Nunito", "sans-serif")
            .lineHeight(1.75.cssRem)
            .margin(top = 2.0.cssRem, bottom = 1.cssRem)
    }
    Breakpoint.SM { Modifier.margin(top = 2.cssRem, bottom = 2.cssRem) }
    Breakpoint.MD { Modifier.margin(top = 2.5.cssRem, bottom = 2.5.cssRem) }
    Breakpoint.LG { Modifier.margin(top = 3.cssRem, bottom = 3.cssRem) }
}

// Section headings (H2)
val SectionHeadingStyle = CssStyle {
    base {
        Modifier
            .fontFamily("Playfair Display", "serif")
            .fontWeight(FontWeight.Bold)
            .lineHeight(2.cssRem)
            .margin(top = 2.cssRem, bottom = 0.625.cssRem)
    }
    Breakpoint.SM { Modifier.margin(top = 1.5.cssRem, bottom = 0.5.cssRem) }
    Breakpoint.MD { Modifier.margin(top = 2.cssRem, bottom = 0.625.cssRem) }
    Breakpoint.LG { Modifier.margin(top = 2.5.cssRem, bottom = 0.75.cssRem) }
}

// Body text
val BodyTextStyle = CssStyle {
    base {
        Modifier
            .fontFamily("Nunito", "sans-serif")
            .fontSize(1.cssRem)
            .lineHeight(1.6)
            .margin(bottom = 1.cssRem)
    }
    Breakpoint.SM { Modifier.margin(top = 0.cssRem, bottom = 0.75.cssRem) }
    Breakpoint.MD { Modifier.margin(top = 0.cssRem, bottom = 1.cssRem) }
    Breakpoint.LG { Modifier.margin(top = 0.cssRem, bottom = 1.25.cssRem) }
}

// Optional divider
val SectionDividerStyle = CssStyle {
    base {
        Modifier
            .margin(top = 2.cssRem, bottom = 2.cssRem)
            .height(1.px)
    }
}

// Headlines (literary serif tone)
val HeadlineTextStyle = CssStyle.base {
    Modifier
        .fontFamily("Playfair Display", "serif")
        .fontSize(3.cssRem)
        .fontWeight(FontWeight.Bold)
        .textAlign(TextAlign.Start)
        .lineHeight(1.2)
        .color(colorMode.toSitePalette().brand.primary) // Deep Teal for headings
}

// Subheadlines
val SubheadlineTextStyle = CssStyle.base {
    Modifier
        .fontFamily("Nunito", "sans-serif")
        .fontSize(1.25.cssRem)
        .textAlign(TextAlign.Start)
        .color(colorMode.toPalette().color.toRgb().copyf(alpha = 0.8f))
}

// Blockquotes
val BlockquoteStyle = CssStyle.base {
    Modifier
        .fontStyle(FontStyle.Italic)
        .lineHeight(1.4)
        .color(colorMode.toSitePalette().brand.primary)
        .borderLeft(4.px, LineStyle.Solid, colorMode.toSitePalette().brand.accent)
        .padding(left = 1.cssRem, top = 0.5.cssRem, bottom = 0.5.cssRem)
        .margin(top = 1.cssRem, bottom = 1.5.cssRem)
}

val BlockquoteCardStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(colorMode.toSitePalette().nearBackground)
            .borderRadius(0.5.cssRem)
            .boxShadow(
                2.px, 2.px, 6.px, color = colorMode.toSitePalette().brand.shadow
            )
            .padding(1.cssRem)
            .margin(top = 2.cssRem, bottom = 2.cssRem)
    }
    Breakpoint.SM {
        Modifier.padding(0.75.cssRem)
    }
    Breakpoint.LG {
        Modifier.padding(1.5.cssRem)
    }
}

val CitationStyle = CssStyle.base {
    Modifier
        .display(DisplayStyle.Block)
        .textAlign(TextAlign.Right)
        .margin(top = 0.25.cssRem)
        .fontSize(0.9.cssRem)
        .color(colorMode.toSitePalette().brand.accent)
}

// Site Buttons

val ButtonStyle = CssStyle {
    base {
        Modifier
            .backgroundImage(colorMode.toSitePalette().brand.gradient) // use SitePalette gradient
            .fontFamily("Nunito", "sans-serif")
            .fontSize(FontSize.Medium)
            .fontWeight(FontWeight.SemiBold)
            .padding(leftRight = 14.px, topBottom = 10.px)
            .borderRadius(6.px)
            .boxShadow(2.px, 3.px, 6.px, 0.px, Colors.Black.copyf(alpha = 0.25f))
            .transition(
                Transition.of("background-image", 250.ms, TransitionTimingFunction.EaseInOut),
                Transition.of("transform", 250.ms, TransitionTimingFunction.EaseInOut),
                Transition.of("box-shadow", 250.ms, TransitionTimingFunction.EaseInOut)
            )
    }
    hover {
        Modifier
            .scale(1.05)
            .boxShadow(3.px, 5.px, 12.px, 0.px, Colors.Black.copyf(alpha = 0.35f))
            .transform { translateY((-2).px) }
    }
    active {
        Modifier
            .scale(0.96)
            .backgroundImage( // darkened gradient for pressed state
                linearGradient(
                    dir = LinearGradient.Direction.ToBottomRight,
                    from = colorMode.toSitePalette().brand.accent.darkened(0.15f),
                    to = colorMode.toSitePalette().brand.primary.darkened(0.15f)
                )
            )
            .boxShadow(0.px, 1.px, 3.px, 0.px, Colors.Black.copyf(alpha = 0.5f), inset = true)
    }
}

val LinkStyle = CssStyle {
    base {
        Modifier
            .color(colorMode.toSitePalette().brand.primary)
            .transition(
                Transition.of(
                    "color",
                    150.ms,
                    TransitionTimingFunction.EaseInOut
                )
            )
    }
    hover {
        Modifier.color(colorMode.toSitePalette().brand.accent)
    }
    active {
        Modifier.color(colorMode.toSitePalette().brand.accent.darkened(0.25f))
    }
    visited {
        Modifier.color(colorMode.toSitePalette().brand.visited)
    }
}

val CircleButtonVariant = ButtonStyle.addVariantBase {
    Modifier.padding(0.px).borderRadius(50.percent)
}

val UncoloredButtonVariant = ButtonStyle.addVariantBase {
    Modifier.setVariable(ButtonVars.BackgroundDefaultColor, Colors.Transparent)
}


// Header Styles
val NavHeaderStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .padding(1.cssRem)
        .backgroundColor(colorMode.toSitePalette().nearBackground)
        .boxShadow(
            0.px, 2.px, 6.px, color = Colors.Black.copyf(alpha = 0.15f)
        )
}

// Footer Styles
val SocialLinkStyle = CssStyle {
    base {
        Modifier
            .color(colorMode.toSitePalette().brand.primary)
            .transition(
                Transition.of(
                    "color",
                    200.ms,
                    TransitionTimingFunction.EaseInOut
                )
            )
            .padding(leftRight = 10.px)
            .zIndex(10)
    }
    hover {
        Modifier.color(colorMode.toSitePalette().brand.accent)
    }
    active {
        Modifier.color(colorMode.toSitePalette().brand.accent.darkened(0.25f))
    }
    visited {
        Modifier.color(colorMode.toSitePalette().brand.visited)
    }
}

val CopyrightStyle = CssStyle {
    base {
        Modifier
            .textAlign(TextAlign.Center)
    }
}

val FooterStyle = CssStyle.base {
    Modifier
        .backgroundColor(colorMode.toSitePalette().nearBackground)
        .padding(topBottom = 1.5.cssRem, leftRight = 10.percent)
        .fillMaxWidth()
}

// Misc Styles
val BackToTopButtonStyle = CssStyle {
    base {
        Modifier
            .padding(0.5.cssRem)
            .fontSize(1.75.cssRem)
            .position(Position.Fixed)
            .right(24.px)
            .bottom(24.px)
            .zIndex(10)
    }
}

val SideMenuStyle = CssStyle.base {
    Modifier
        .fillMaxHeight()
        .width(clamp(12.cssRem, 33.percent, 18.cssRem))
        .padding(top = 1.cssRem, leftRight = 1.cssRem)
        .gap(1.5.cssRem)
        .backgroundColor(colorMode.toSitePalette().nearBackground)
        .zIndex(10)
        .borderRadius(topLeft = 2.cssRem)
}


val ModalExternalCardStyle = CssStyle {
    base {
        Modifier
            .fillMaxSize()
            .position(Position.Fixed)
            .top(0.px)
            .left(0.px)
            .backgroundColor(colorMode.toSitePalette().brand.shadow)
            .zIndex(100)
    }
}

val ModalInternalCardStyle = CssStyle {
    base {
        Modifier
            .maxWidth(90.percent)
            .minWidth(300.px)
            .maxHeight(85.percent)
            .backgroundColor(colorMode.toSitePalette().nearBackground)
            .borderRadius(24.px)
            .boxShadow(0.px, 8.px, 24.px, 0.px, colorMode.toSitePalette().brand.shadow)
            .padding(24.px)
    }
}

val WritingCardStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding { 16.px }
            .margin { 16.px }
            .backgroundColor(colorMode.toSitePalette().nearBackground)
            .borderRadius(16.px)
            .boxShadow(0.px, 4.px, 16.px, 0.px, colorMode.toSitePalette().brand.shadow)
    }
}

val CoverImageStyle = CssStyle.base {
    Modifier
        .width(100.percent)
        .borderRadius(12.px)
}

val DropdownStyle = CssStyle.base {
    Modifier
        .position(Position.Absolute)
        .top(100.percent)
        .backgroundColor(colorMode.toSitePalette().nearBackground)
        .boxShadow(
            offsetX = 0.px,
            offsetY = 2.px,
            blurRadius = 6.px,
            color = colorMode.toSitePalette().brand.shadow,
        )
        .borderRadius(0.5.cssRem)
        .padding(0.5.cssRem)
        .gap(0.5.cssRem)
        .zIndex(10)
        .whiteSpace(WhiteSpace.NoWrap)
        .width(Width.MaxContent)
}

// Define the keyframes for a continuous rotation
val SpinKeyframes = Keyframes {
    from {
        Modifier.rotate(0.deg)
    }
    to {
        Modifier.rotate(360.deg)
    }
}

// Define a style that applies the animation to any component
val SpinnerStyle = CssStyle.base {
    Modifier
        .size(50.px)
        .position(Position.Relative)
        .animation(SpinKeyframes.toAnimation(
            duration = 1.5.s,
            iterationCount = AnimationIterationCount.Infinite,
        )
        )
        .zIndex(10)
}

val ChipStyle = CssStyle {
    base {
        Modifier
            .textAlign(TextAlign.Center)
            .borderRadius(1.cssRem)
            .padding(leftRight = 0.75.cssRem, topBottom = 0.4.cssRem)
            .cursor(Cursor.Pointer)
            .color(colorMode.toSitePalette().brand.accentText)
            .whiteSpace(WhiteSpace.NoWrap)
    }
}

val FeaturedStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .borderRadius(r = 1.cssRem)
            .fontStyle(FontStyle.Italic)
            .fontWeight(FontWeight.SemiBold)
            .backgroundColor(colorMode.toSitePalette().brand.accent)
            .color(colorMode.toSitePalette().brand.accentText)
    }
}

val CategoryStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .flexWrap(FlexWrap.Wrap)
            .margin(bottom = 2.cssRem)
    }
}
