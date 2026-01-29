package com.sorenkai.web.state

sealed class ActionResult {
    data object Success : ActionResult()
    data class Failure(val errorMessage: String) : ActionResult()
}
