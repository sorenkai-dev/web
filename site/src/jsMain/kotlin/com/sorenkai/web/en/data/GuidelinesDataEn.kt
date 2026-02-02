package com.sorenkai.web.en.data

import com.sorenkai.web.components.interfaces.IGuidelines
import com.sorenkai.web.components.interfaces.IProhibitedContent

object GuidelinesDataEn : IGuidelines {
    override val title = "Community Guidelines"
    override val openingParagraph =
        "Welcome to SorenKai.com, an independent publication and community space focused on " +
            "technology, writing, and the human side of AI. These Community Guidelines exist to " +
            "set clear expectations for participation and to protect the quality of discussion " +
            "on this site. By participating in community discussions, you agree to follow these " +
            "guidelines. Violations may result in moderation actions as described below."
    override val section1heading = "Purpose"
    override val section1paragraph =
        "This community exists to support thoughtful, respectful discussion. The goal is " +
            "conversation, not confrontation, and participation assumes good faith, curiosity, " +
            "and mutual respect. By posting here, you agree to follow these guidelines."
    override val section2heading = "Expected Conduct"
    override val section2paragraph1 = "We expect participants to:"
    override val section2ul =
        listOf(
            "Engage respectfully, even when disagreeing",
            "Address ideas rather than attacking people",
            "Assume good intent unless clearly shown otherwise",
            "Keep discussions relevant to the topic at hand",
            "Contribute constructively rather than disruptively"
        )
    override val section2paragraph2 = "Disagreement is welcome. Disrespect is not."
    override val section3heading = "Prohibited Content and Behavior"
    override val section3paragraph = "The following are not allowed:"
    override val prohibitedContent = object : IProhibitedContent {
        override val section1heading = "Harassment and Abuse"
        override val uList1 =
            listOf(
                "Personal attacks, insults, or intimidation",
	            "Targeted harassment or repeated bad-faith engagement",
	            "Threats of violence or harm"
            )
        override val section2heading = "Hate, Dehumanization, and Contempt"
        override val section2paragraph1 =
            "This community does not permit content that is demeaning, dehumanizing, or " +
                "contemptuous toward individuals or groups. This includes:"
        override val section2Ulist =
            listOf(
                "Slurs, insults, or derogatory labels directed at groups of people",
                "Dehumanizing language, including reducing people to animals, demons, vermin, or caricatures",
                "Politically derogatory name-calling (e.g., mocking or obscene labels for ideological groups or voters)",
                "Broad generalizations that portray groups of people as inherently stupid, evil, or subhuman"
            )
        override val section2paragraph2 =
            "This standard applies regardless of political affiliation, ideology, or belief. You " +
                "are free to criticize ideas, policies, movements, and public figures. You are " +
                "not free to attack people as a category or to use contempt as a substitute for " +
                "argument. If your point cannot be made without insult, it does not belong here."
        override val section3heading = "Violence and Illegal Activity"
        override val uList3 =
            listOf(
                "Threats or glorification of violence",
                "Content that promotes or facilitates illegal activity"
            )
        override val section4heading = "Sexual Content"
        override val section4paragraph1 = "This community is not a venue for sexual content. The following are not permitted:"
        override val section4UL1 =
            listOf(
                "Sexualized or explicit material",
                "Graphic descriptions of sexual acts",
                "Content intended to arouse or shock"
            )
        override val section4paragraph2 = "That said, serious discussion of sexual violence, assault, or abuse is allowed when it is:"
        override val section4UL2 =
            listOf(
                "Factual",
                "Contextual",
                "Relevant to a news event, legal case, historical discussion, or broader social issue"
            )
        override val section4paragraph3 =
            "Discussions should focus on the facts, consequences, and implications, not explicit " +
                "depiction of the acts themselves. We do not require euphemisms or coded " +
                "language. Mature topics may be discussed maturely."
        override val section5heading = "Spam and Manipulation"
        override val section5UL =
            listOf(
                "Unsolicited advertising",
                "Repetitive or disruptive posting",
                "Coordinated attempts to derail or dominate discussion",
                "Deliberate misinformation intended to mislead"
            )
        override val section6heading = "Impersonation"
        override val section6UL =
            listOf(
                "Pretending to be another individual or entity in a deceptive or harmful way"
            )
    }
    override val section4heading = "Moderation"
    override val section4paragraph =
        "Moderation exists to protect the health of the conversation. This is a privately " +
            "operated community. Participation is a privilege, not a right. Moderation decisions " +
            "are guided by the health of the conversation, not by claims of neutrality or " +
            "“free speech.” Content may be removed if it undermines thoughtful discussion, even " +
            "if it is not illegal. If your participation consistently lowers the quality of " +
            "discourse, it may be limited or removed."
    override val section5heading = "High-Conflict Topics and Thread Locking"
    override val section5paragraph1 =
        "Some topics have a well-established pattern of devolving into culture-war posturing, " +
            "personal attacks, or bad-faith argument, regardless of the intent of the original " +
            "post. When this happens, moderators may:"
    override val section5UL1 =
        listOf(
            "Lock the thread to prevent further discussion",
            "Remove the post and ask the original poster to edit their post to avoid further confusion"
        )
    override val section5paragraph2 =
        "This may occur even if individual comments have not yet violated specific rules. Topics " +
            "that frequently fall into this category include, but are not limited to:"
    override val section5UL2 =
        listOf(
            "Gender identity and trans-related debates framed as culture-war arguments",
            "Highly polarized political identity disputes",
            "Posts primarily intended to provoke, bait, or score ideological points"
        )
    override val section5paragraph3 =
        "Locking a thread is not a judgment on the legitimacy of the topic itself. It is a " +
            "judgment about whether the conversation, as it is unfolding, can remain productive " +
            "and respectful in this space. Actions are taken based on behavior and context, not " +
            "ideology or opinion. Moderation decisions are final."
    override val section6heading = "Reporting"
    override val section6paragraph =
        "If you encounter content that violates these guidelines, please report it using the " +
            "available reporting tools or contact the site administrator. Reports should be " +
            "made in good faith. Abuse of reporting mechanisms may result in action."
    override val section7heading = "Consequences"
    override val section7paragraph1 = "Violations may result in:"
    override val section7UL =
        listOf(
            "Content removal",
            "Temporary suspension",
            "Permanent removal from the community"
        )
    override val section7paragraph2 = "Severity and frequency of violations are taken into account."
    override val closingHeading = "Closing Notes"
    override val closingParagraph1 =
        "This space is intentionally curated. It exists for people who want to think, discuss, " +
            "and disagree without resorting to contempt, mockery, or dehumanization. If that " +
            "feels restrictive, this community may not be a good fit, and that is okay."
    override val closingParagraph2 = "Be curious. Be respectful. Disagree thoughtfully."
}
