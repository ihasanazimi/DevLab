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
        showLoading.value = false
    }

    // for KotlinCoroutines
    private val _developerInfoForKotlinCoroutines = MutableSharedFlow<DeveloperInfo>()
    val developerInfoForKotlinCoroutines = _developerInfoForKotlinCoroutines.asSharedFlow()

    // for RxAndroid|Kotlin
    val developerInfoForRxAndroid = MutableLiveData<DeveloperInfo>()

    /** this fields has shared between top data holder */
    val showLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()


    fun getDeveloperInfoByKotlinCoroutines() {
        Log.i(TAG, "getDeveloperInfoByKotlinCoroutines: ")
        viewModelScope.launch(coroutineExceptionHandler) {
            developerInfoUseCase.getDeveloperInfo().collect {
                _developerInfoForKotlinCoroutines.emit(it)
            }
        }
    }




    private val compositeDisposable = CompositeDisposable()

    fun getDeveloperInfoByRxKotlin() {
        Log.i(TAG, "getDeveloperInfoByRxKotlin: ")
        developerInfoUseCase.getDeveloperInfoByRx()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading.value = true }
            .doOnComplete { showLoading.value = false }
            .doOnError {
                showLoading.value = false
                errorMessage.value = it.message
            }
            .subscribeBy(
                onNext = { developerInfoForRxAndroid.value = it },
                onError = {
                    errorMessage.value = it.message
                    showLoading.value = false
                          },
                onComplete = { Log.i(TAG, "getDeveloperInfoByRxKotlin: onComplete ") }
            ).addTo(compositeDisposable)
    }





    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
        compositeDisposable.clear()
    }
}