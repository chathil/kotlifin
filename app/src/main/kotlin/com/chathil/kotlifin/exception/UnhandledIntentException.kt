package com.chathil.kotlifin.exception

import androidx.lifecycle.ViewModel

class UnhandledIntentException(viewModel: ViewModel, intent: Any) :
    Exception("Intent ${intent::class.java} is not handled in ${viewModel::class.java}")