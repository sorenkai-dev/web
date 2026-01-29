package com.sorenkai.web.state.discussions

sealed class DiscussionsUiState {
    data object Loading : DiscussionsUiState()
    data object Ready: DiscussionsUiState()
    data class Error(val errorMessage: String) : DiscussionsUiState()
}
