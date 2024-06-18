package ir.ha.meproject.ui.fragments.developer_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import ir.ha.meproject.model.use_cases.DeveloperInfoUseCase
import ir.ha.meproject.utility.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeveloperInfoFragmentVM @Inject constructor(
    private val developerInfoUseCase: DeveloperInfoUseCase
) : BaseViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ m , t ->
        errorMessage.value = (t.message.toString())
    }


    private val compositeDisposable = CompositeDisposable()

    private val _developerInfoFlow = MutableSharedFlow<DeveloperInfo>()
    val developerInfoFlow = _developerInfoFlow.asSharedFlow()

    val developerInfoLiveData = MutableLiveData<DeveloperInfo>()

    val errorMessage = MutableLiveData<String>()

    val showLoading = MutableLiveData<Boolean>()

    fun getDeveloperInfoByKotlinCoroutines() {
        Log.i(TAG, "getDeveloperInfoByKotlinCoroutines: ")
        showLoading.value = true
        viewModelScope.launch(coroutineExceptionHandler) {
            developerInfoUseCase.getDeveloperInfo().collect {
                _developerInfoFlow.emit(it)
            }
        }
    }

    fun getDeveloperInfoByRxKotlin() {
        Log.i(TAG, "getDeveloperInfoByRxKotlin: ")
        showLoading.value = true
        developerInfoUseCase.getDeveloperInfoByRx()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { showLoading.value = false },
                onNext = { developerInfoLiveData.value = it },
                onError = {
                    showLoading.value = false
                    errorMessage.value = it.message
                }
            ).addTo(compositeDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
        compositeDisposable.clear()
    }
}