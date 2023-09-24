package com.chathil.kotlifin.ui.main.mvi

data class State(
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        val Initial = State(isLoading = false, error = null)
    }
}
