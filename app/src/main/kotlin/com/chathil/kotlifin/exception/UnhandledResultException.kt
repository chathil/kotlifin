package com.chathil.kotlifin.exception

import androidx.lifecycle.ViewModel

class UnhandledResultException(viewModel: ViewModel, result: Any) :
    Exception("Action ${result::class.java} is not handled in ${viewModel::class.java}")