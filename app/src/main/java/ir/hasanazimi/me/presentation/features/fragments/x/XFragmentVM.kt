package ir.hasanazimi.me.presentation.features.fragments.x

import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.me.common.base.BaseViewModel
import ir.hasanazimi.me.domain.XUseCase
import javax.inject.Inject


@HiltViewModel
class XFragmentVM @Inject constructor(
    private val xUseCase: XUseCase
) : BaseViewModel() {

}