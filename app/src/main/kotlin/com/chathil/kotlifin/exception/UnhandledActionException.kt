package com.chathil.kotlifin.exception

import androidx.lifecycle.ViewModel

class UnhandledActionException(viewModel: ViewModel, action: Any) :
    Exception("Action ${action::class.java} is not handled in ${viewModel::class.java}")