package ir.hasanazimi.androidlab.presentation.features.fragments.x

import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.androidlab.common.base.BaseViewModel
import ir.hasanazimi.androidlab.domain.XUseCase
import javax.inject.Inject


@HiltViewModel
class XFragmentVM @Inject constructor(
    private val xUseCase: XUseCase
) : BaseViewModel() {

}