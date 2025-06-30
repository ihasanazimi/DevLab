package ir.hasanazimi.devlab.presentation.features.fragments.x

import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.devlab.common.base.BaseViewModel
import ir.hasanazimi.devlab.domain.XUseCase
import javax.inject.Inject


@HiltViewModel
class XFragmentVM @Inject constructor(
    private val xUseCase: XUseCase
) : BaseViewModel() {

}