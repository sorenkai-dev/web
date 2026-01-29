package com.sorenkai.web.en.content

import androidx.compose.runtime.Composable
import com.sorenkai.web.components.widgets.LeadParagraph
import com.sorenkai.web.components.widgets.SectionHeader
import com.sorenkai.web.components.widgets.SectionParagraph
import com.sorenkai.web.toSitePalette
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.*

@Composable
fun PrivacyEnContent(breakpoint: Breakpoint) {
    val update = "October 21, 2025"
    val version = "1.0"

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        H1 (
            attrs = Modifier.align(Alignment.CenterHorizontally).toAttrs(),
            content = { Text("Privacy Policy") }
        )

        LeadParagraph(breakpoint) {
            Text("This page contains the Privacy Policy, effective $update, for sorenkai.com and its related " +
                "subdomains (collectively the \"Site\"). Soren Kai, or his designated representative (\"we\", " +
                "\"us\", or \"our\"), is solely responsible for the Site and its content."
            )
        }

        SectionHeader("1. Information We Collect", breakpoint)

        SectionParagraph(breakpoint) {
            Text("We collect only the information necessary to operate the Site and its limited community features:")
            Ul {
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Authentication Data: ") }
                    )
                    Text("Email address or basic sign-in credentials from your chosen provider (Google, " +
                        "Microsoft, etc.) used solely through Zitadel to prevent spam and automated " +
                        "abuse. These accounts do not create public profiles or social features.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Content Data: ") }
                    )
                    Text("Comments or discussion posts you choose to submit.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Operational Data: ") }
                    )
                    Text("Timestamps, internal IDs, and minimal technical metadata (e.g., IP address, error " +
                        "logs) needed for security, rate-limiting, and diagnostics.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Communications: ") }
                    )
                    Text("Emails you send to us (support, moderation appeals, or privacy requests).")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Newsletter Data: ") }
                    )
                    Text("If you subscribe to updates, we collect your email address and preferences.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Cookies & Analytics: ") }
                    )
                    Text("We may use basic analytics tools (e.g., Plausible Analytics, Google Analytics) to " +
                        "understand traffic patterns and improve the Site. This data is aggregated or anonymized and " +
                        "not used for profiling or advertising. Where required by law, we will ask for your consent " +
                        "before setting non-essential cookies.")
                }
            }
        }

        SectionHeader("2. How We Use Information", breakpoint)

        SectionParagraph(breakpoint) {
            Text("We use the information we collect to:")
            Ul {
                Li { Text("Authenticate access and prevent spam or automated abuse.") }
                Li { Text("Display and moderate community content.") }
                Li { Text("Operate newsletters and optional email updates (with opt-out available).") }
                Li { Text("Maintain Site performance, reliability, and security.") }
                Li { Text("Communicate with you when you contact us.") }
                Li { Text("Analyze aggregate traffic to improve the Site") }
            }
            Text("We do not sell or monetize personal data.")
        }

        SectionHeader("3. Legal Bases for Processing", breakpoint)

        SectionParagraph(breakpoint) {
            Text("For visitors in the EEA, UK, or Switzerland, data is processed under one or more of these " +
                "legal bases:")
            Ul {
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Contract: : ") }
                    )
                    Text("To provide and maintain the Site and its functions.") }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Consent: ") }
                    )
                    Text("For newsletters, optional communications, and non-essential cookies") }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Legitimate Interests: ") }
                    )
                    Text("For moderation, abuse prevention, and Site improvement, balanced against your rights.") }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Legal Obligation: ") }
                    )
                    Text("When necessary to comply with applicable laws.")
                }
            }
        }

        SectionHeader("4. Your Rights", breakpoint)

        SectionParagraph(breakpoint) {
            Text("If you are located in the EEA, UK, or Switzerland, you may have the following rights:")
            Ul {
                Li { Text("Access the data we hold about you.") }
                Li { Text("Correct inaccuracies.") }
                Li { Text("Request deletion (“right to be forgotten”).") }
                Li { Text("Restrict or object to certain processing.") }
                Li { Text("Request a copy of your data (portability).") }
                Li { Text("File a complaint with your local data protection authority.") }
            }
            Text("To exercise any of these rights, contact ")
            Link("mailtio:privacy@sorenkai.com?subject=Rights", "privacy@sorenkai.com" )
        }

        SectionHeader("5. Sharing", breakpoint)

        SectionParagraph(breakpoint) {
            Ol {
                Li { Text("We do not sell or rent personal data.") }
                Li {
                    Text("Limited information may be processed on our behalf by trusted service providers " +
                        "that help us operate the Site, such as:")
                    Ul {
                        Li { Text("Zitadel for authentication and basic analytics") }
                        Li { Text("MongoDB Atlas for database storage.") }
                        Li { Text("Hosting/CDN providers (for site delivery and caching).") }
                        Li { Text("Newsletter providers (if subscribed).") }
                    }
                }
                Li { Text("All such providers act under written agreements and may only process data on " +
                    "our behalf.") }
                Li { Text("We may disclose data when legally required or necessary to protect rights, " +
                    "safety, or system integrity.") }
            }
        }

        SectionHeader("6. Retention", breakpoint)

        SectionParagraph(breakpoint) {
            Ul {
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Authentication Data: ") }
                    )
                    Text("Retained while you maintain an active login")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Content: ") }
                    )
                    Text("Retained until deleted or anonymized; removed content may persist briefly in backups.")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Newsletter Data: ") }
                    )
                    Text("Retained until you unsubscribe")
                }
                Li {
                    Span(
                        attrs = Modifier
                            .fontWeight(FontWeight.Bold)
                            .color(ColorMode.current.toSitePalette().brand.primary)
                            .toAttrs(),
                        content = { Text("Operational Logs: ") }
                    )
                    Text("Typically kept 90–180 days, unless needed longer for security or compliance.")
                }
            }
            Text("We retain personal data only as long as necessary for these purposes or as required by law, " +
                "after which it is deleted or anonymized.")
        }

        SectionHeader("7. Children", breakpoint)

        SectionParagraph(breakpoint) {
            Text("The Site is not directed to children under 13 years of age in the U.S. or under the minimum " +
                "legal age required in your jurisdiction (up to 16 in parts of the EU). We do not knowingly collect " +
                "personal data from children. If we learn that we have done so, we will promptly delete it.")
        }

        SectionHeader("8. Security", breakpoint)

        SectionParagraph(breakpoint) {
            Text("We use industry-standard safeguards, including:")
            Ul {
                Li { Text("HTTPS encryption for all data in transit.") }
                Li { Text("Zitadel authentication and access controls.") }
                Li { Text("Role-based, least-privilege permissions on backend systems.") }
            }
            Text("While no system can guarantee complete security, we take all reasonable measures to protect " +
                "your information.")
        }

        SectionHeader("9. International Transfers", breakpoint)

        SectionParagraph(breakpoint) {
            Text("Data may be processed in the United States or other locations where our service providers " +
                "operate. For users in the EEA, UK, or Switzerland, we rely on Standard Contractual Clauses (SCCs) " +
                "or other approved mechanisms to ensure adequate data protection when transferring data outside your " +
                "region.")
        }

        SectionHeader("10. External Links", breakpoint)

        SectionParagraph(breakpoint) {
            Text("The Site may link to third-party platforms including, but not limited to, Substack, Patreon, " +
                "GitHub, or TikTok. We are not responsible for the privacy practices of those external services. " +
                "Your use of those platforms is at your own discretion and subject to their respective policies.")
        }

        SectionHeader("11. Updates to This Policy", breakpoint)

        SectionParagraph(breakpoint) {
            Text("We may update this Privacy Policy periodically. Material updates will be announced on the " +
                "Site, and the “last updated” date will be revised accordingly. Your continued use of the Site " +
                "after changes take effect constitutes acceptance of the updated Policy.")
        }

        SectionHeader("12. Contact", breakpoint)

        SectionParagraph(breakpoint) {
            Text("For privacy questions or data requests, contact: ")
            Link("mailto:privacy@sorenkai.com", "privacy@sorenkai.com")
        }

        SectionParagraph(breakpoint, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row { Text("For GDPR purposes, the data controller is:") }
                Row { Text("Soren Kai") }
                Row { Text("United States") }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(topBottom = 4.cssRem),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SectionParagraph(breakpoint) { Text("Last Updated: $update") }
            SectionParagraph(breakpoint) { Text("Version $version") }
        }

    }
}
