package com.chathil.kotlifin.ui.feature.settings.mvi

import com.chathil.kotlifin.data.model.session.ActiveSession

sealed interface Action {
    data class SaveActiveSession(val session: ActiveSession) : Action
}
