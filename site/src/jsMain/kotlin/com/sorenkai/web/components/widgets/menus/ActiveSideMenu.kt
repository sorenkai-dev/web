package com.sorenkai.web.components.widgets.menus

sealed class ActiveSideMenu {
    object None : ActiveSideMenu()
    data class Menu(val mode: SideNavMode) : ActiveSideMenu()
}
