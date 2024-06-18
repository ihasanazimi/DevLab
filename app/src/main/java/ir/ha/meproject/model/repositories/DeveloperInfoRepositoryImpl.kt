package ir.ha.meproject.model.repositories

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import ir.ha.meproject.model.network.WebServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class DeveloperInfoRepositoryImpl @Inject constructor(
    private val webServices: WebServices,
) : DeveloperInfoRepository {


    override suspend fun getDeveloperInfo(): Flow<DeveloperInfo> = flow {
        emit(webServices.getDeveloperInformation())
    }


    override fun getDeveloperInfoByRx(): Observable<DeveloperInfo> {
        return webServices.getDeveloperInformationByRx().toObservable()
            .subscribeOn(Schedulers.io()) // Perform network request on IO thread
            .onErrorReturn { // Handle errors and provide fallback
                DeveloperInfo()
            }
            .timeout(10, TimeUnit.SECONDS) // Set timeout for the request
            .doOnError { error -> // Handle timeout separately
                if (error is TimeoutException) {
                    println("Timeout occurred while fetching users")
                }
            }
    }
}