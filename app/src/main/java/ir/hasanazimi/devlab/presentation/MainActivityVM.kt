package ir.hasanazimi.devlab.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hasanazimi.devlab.domain.XUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val xUseCase: XUseCase
) : ViewModel() {

}