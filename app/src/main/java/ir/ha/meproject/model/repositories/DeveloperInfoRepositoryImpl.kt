package ir.ha.meproject.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import ir.ha.meproject.model.network.WebServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeveloperInfoRepositoryImpl @Inject constructor(
    private val webServices: WebServices,
) : DeveloperInfoRepository {


    override suspend fun getDeveloperInfo(): Flow<DeveloperInfo> = flow {
        emit(webServices.getDeveloperInformation())
    }


    override fun getDeveloperInfoByRx(): Single<DeveloperInfo> {
        return webServices.getDeveloperInformationByRx()
    }
}