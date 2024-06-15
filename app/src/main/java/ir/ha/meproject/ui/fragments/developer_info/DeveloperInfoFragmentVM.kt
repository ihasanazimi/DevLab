package ir.ha.meproject.ui.fragments.developer_info

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import ir.ha.meproject.model.use_cases.DeveloperInfoUseCase
import ir.ha.meproject.utility.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeveloperInfoFragmentVM @Inject constructor(
    private val developerInfoUseCase: DeveloperInfoUseCase
) : BaseViewModel() {


    private val _developerInfo = MutableSharedFlow<DeveloperInfo>()
    val developerInfo = _developerInfo.asSharedFlow()

    fun getDeveloperInfoByKotlinCoroutines() {
        viewModelScope.launch {
            developerInfoUseCase.getDeveloperInfo().collect{
                _developerInfo.emit(it)
            }
        }
    }


    fun getDeveloperInfoByRX(){

    }

}