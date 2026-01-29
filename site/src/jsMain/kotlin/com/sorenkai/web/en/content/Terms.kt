package com.sorenkai.web.en.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.*

@Composable
fun TermsEnContent(breakpoint: Breakpoint) {
    val update = "10-21-2025"
    val version = "1.0"

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        H1(
            attrs = Modifier.align(Alignment.CenterHorizontally).toAttrs(),
            content = { Text("Terms of Service") }
        )

        SectionParagraph(
            breakpoint,
            content = { Text("Welcome to SorenKai.com, an independent publication and community " +
                "platform exploring technology, writing, and the human side of AI. By accessing or using " +
                "this website (the “Site”), you agree to these Terms of Service (“Terms”) and our Community " +
                "Guidelines and Privacy Policy.") }
        )
        SectionParagraph(
            breakpoint,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fontStyle(FontStyle.Italic)
                .fontWeight(FontWeight.SemiBold)
                .color(Colors.Red),
            content = { Text("If you do not agree, please do not use the Site.") }
        )

        SectionHeader("1. Purpose and Scope", breakpoint)

        SectionParagraph(
            breakpoint,
            content = { Text("SorenKai.com (“we,” “us,” or “our”) is a publication and discussion space " +
                "operated by Soren Kai. These Terms govern your access to and use of all Site content and features, " +
                "including writings, essays, community discussions, and comment areas (collectively, the “Services”)." )
            }
        )

        SectionHeader("2. Eligibility", breakpoint)

        SectionParagraph(
            breakpoint,
            content = { Text("This Site is open to the general public. However, participation in community " +
                "discussions is intended for users aged 13 and older (or the minimum legal age in your jurisdiction). " +
                "If you are under that age, please do not submit comments or other interactive content.")
            }
        )

        SectionHeader("3. Community Participation", breakpoint)

        SectionParagraph(
            breakpoint,
            content = { Text("SorenKai.com includes limited interactive features such as comments and discussions. " +
                "Accounts are managed through Zitadel solely to prevent spam and automated abuse. " +
                "These accounts do not create public profiles, private messaging, or user pages.")
            }
        )

        SectionParagraph(breakpoint) {
            Text("By participating, you agree to:")
            Ul {
                Li { Text("Post only content that complies with our Community Guidelines;") }
                Li { Text("Avoid harassment, hate speech, or illegal activity;") }
                Li { Text("Take responsibility for your contributions.") }
            }
            Text("We reserve the right to remove content, disable access, or restrict participation for " +
                "violations of these Terms or our Community Guidelines.")
        }

        SectionHeader("4. User Content", breakpoint)

        SectionParagraph(breakpoint) {
            Text("You retain ownership of the content you submit (such as comments or discussion posts.")
        }

        SectionParagraph(breakpoint) {
            Text("By submitting content to the Site, you grant Soren Kai a non-exclusive, worldwide, royalty-free, " +
                "perpetual license to:")
            Ol {
                Li { Text("Display, distribute, and archive your content as part of the Site’s operation;") }
                Li { Text("Use, reproduce, quote, or adapt your content in future works — including articles, " +
                    "essays, books, or multimedia publications produced under the Soren Kai name, whether in print," +
                    " digital, or audio form.") }
            }
            Text("Where feasible, contributors will be credited by name or username when their public comments " +
                "or contributions are referenced in such works.")
        }

        SectionParagraph(breakpoint) {
            Text("You may request deletion of your content by contacting ")
            Link("mailto:support@sorenkai.com?subject=Content Removal", "support@sorenkai.com")
            Text(". We’ll make reasonable efforts to remove it from public view, though content already used " +
                "in published materials may remain in those contexts.")
        }

        SectionHeader("5. Intellectual Property", breakpoint)

        SectionParagraph(breakpoint) {
            Text("All original writings, designs, and materials on this Site, including, but not limited to, " +
                "essays, articles, branding, and imagery, are the intellectual property of Soren Kai unless otherwise " +
                "noted. You may not copy, reproduce, or distribute any portion of the Site without prior written consent.")
        }

        SectionParagraph(breakpoint) {
            Text("Guest contributions remain the property of their respective authors, used under permission.")
        }

        SectionHeader("6. Enforcment and Moderation", breakpoint)

        SectionParagraph(breakpoint) {
            Text("We review reports in good faith and may take actions such as:")
            Ul {
                Li { Text("Removing or editing content:") }
                Li { Text("Issuing warnings:") }
                Li { Text("Temporarily or permanently disabling discussion privileges.") }
            }
            Text ("If you believe a moderation decision was made in error, you may appeal by emailing ")
            Link("mailto:appeals@sorenkai.com?subject=Moderation Appeal", "appeals@sorenkai.com")
            Text(". All decisions on appeals are final.")
        }

        SectionHeader("7. Disclaimers", breakpoint)

        SectionParagraph(breakpoint) {
            Text("This Site and its content are provided “as is” and “as available. We make no warranties, " +
                "express or implied, regarding accuracy, reliability, or availability. We do not guarantee uninterrupted " +
                "access or freedom from technical issues. You understand that participation in discussions is at " +
                "your own discretion and risk.")
        }

        SectionHeader("8. Limitation of Liability", breakpoint)

        SectionParagraph(breakpoint) {
            Text("To the fullest extent permitted by law, SorenKai.com, its owner, and affiliates shall not be " +
                "liable for any indirect, incidental, or consequential damages arising from your use of the Site or its " +
                "content. Our total liability shall not exceed $100 USD or the amount you paid to us (if any), whichever " +
                "is greater.")
        }

        SectionHeader("9. Governing Law", breakpoint)

        SectionParagraph(breakpoint) {
            Text("These Terms are governed by the laws of the State of New Jersey, without regard to conflict " +
                "of law principles. Any disputes arising under these Terms shall be resolved in the state or federal " +
                "courts located in Camden County, New Jersey, unless otherwise required by law.")
        }

        SectionHeader("10. Updates to These Terms", breakpoint)

        SectionParagraph(breakpoint) {
            Text("We may update these Terms from time to time. If we make material changes, we’ll post the " +
                "new version on this page with an updated effective date. Your continued use of the Site after " +
                "such updates constitutes acceptance of the revised Terms.")
        }

        SectionHeader("11. Contact", breakpoint)

        SectionParagraph(breakpoint) {
            Text("For questions about these Terms, contact: ")
            Link("mailto:contact@sorenkai.com?subject=ToS Questions", "contact@sorenkai.com")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(topBottom = 4.cssRem),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SectionParagraph(breakpoint) { Text("Last updated: $update") }
            SectionParagraph(breakpoint) { Text("Version $version") }
        }
    }
}
