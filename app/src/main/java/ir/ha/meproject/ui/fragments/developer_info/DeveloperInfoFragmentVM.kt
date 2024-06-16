package ir.ha.meproject.ui.fragments.developer_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
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

    private val compositeDisposable = CompositeDisposable()

    private val _developerInfo = MutableSharedFlow<DeveloperInfo>()
    val developerInfo = _developerInfo.asSharedFlow()

    val developerInfoLiveData = MutableLiveData<DeveloperInfo>()

    fun getDeveloperInfoByKotlinCoroutines() {
        Log.i(TAG, "getDeveloperInfoByKotlinCoroutines: ")
        viewModelScope.launch {
            developerInfoUseCase.getDeveloperInfo().collect {
                _developerInfo.emit(it)
            }
        }
    }

    fun getDeveloperInfoByRxKotlin() {
        Log.i(TAG, "getDeveloperInfoByRxKotlin: ")
        val observable = developerInfoUseCase.getDeveloperInfoByRx()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    developerInfoLiveData.value = it
                },
                onError = {
                    println("HSN_Error: ${it.message}")
                }
            )
        compositeDisposable.add(observable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}