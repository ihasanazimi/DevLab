package ir.hasanazimi.devlab.common.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    open val TAG = this::class.java.simpleName
}