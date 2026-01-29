package com.sorenkai.web.components.widgets.menus

sealed class SideNavMode {
    object Navigation : SideNavMode()
    object Settings : SideNavMode()
    object Admin : SideNavMode()
    object Moderator : SideNavMode()
}
