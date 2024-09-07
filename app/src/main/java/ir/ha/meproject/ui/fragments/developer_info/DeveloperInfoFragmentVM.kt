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

    // for KotlinCoroutines
    private val _developerInfoForKotlinCoroutines = MutableSharedFlow<DeveloperInfo>()
    val developerInfoForKotlinCoroutines = _developerInfoForKotlinCoroutines.asSharedFlow()

    // for RxAndroid|Kotlin
    val developerInfoForRxAndroid = MutableLiveData<DeveloperInfo>()

    /** this fields has shared between top data holder */
    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading = _showLoading
    val errorMessage = MutableLiveData<String>()


    fun getDeveloperInfoByKotlinCoroutines() {
        Log.i(TAG, "getDeveloperInfoByKotlinCoroutines: ")
        _showLoading.value = true
        viewModelScope.launch {
            runCatching {
                developerInfoUseCase.getDeveloperInfo().collect {
                    _developerInfoForKotlinCoroutines.emit(it)
                }
            }.onFailure {
                Log.e(TAG, "getDeveloperInfoByKotlinCoroutines - happen error ")
                errorMessage.value = (it.message.toString())
                _showLoading.value = false
            }.onSuccess {
                Log.d(TAG, "getDeveloperInfoByKotlinCoroutines - Completed and done successfully ")
                _showLoading.value = false
            }
        }
    }




    private val compositeDisposable = CompositeDisposable()

    fun getDeveloperInfoByRxKotlin() {
        Log.i(TAG, "getDeveloperInfoByRxKotlin: ")
        developerInfoUseCase.getDeveloperInfoByRx()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _showLoading.value = true }
            .doOnComplete { _showLoading.value = false }
            .doOnError {
                Log.e(TAG, "getDeveloperInfoByRxKotlin - happen error ")
                _showLoading.value = false
                errorMessage.value = it.message
            }
            .subscribeBy(
                onNext = { developerInfoForRxAndroid.value = it },
                onError = {
                    Log.e(TAG, "getDeveloperInfoByRxKotlin - happen error ")
                    errorMessage.value = it.message
                    _showLoading.value = false
                          },
                onComplete = { Log.d(TAG, "getDeveloperInfoByRxKotlin - Completed and done successfully ") }
            ).addTo(compositeDisposable)
    }





    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        Log.i(TAG, "onCleared: ")
    }
}