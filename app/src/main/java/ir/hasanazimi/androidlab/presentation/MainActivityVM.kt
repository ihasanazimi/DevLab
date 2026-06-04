package ir.hasanazimi.androidlab.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.androidlab.common.base.BaseViewModel
import ir.hasanazimi.androidlab.domain.XUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val xUseCase: XUseCase
) : BaseViewModel() {

}