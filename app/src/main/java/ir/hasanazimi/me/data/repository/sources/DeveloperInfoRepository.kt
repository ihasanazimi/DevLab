package ir.hasanazimi.me.data.repository.sources

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.hasanazimi.me.data.entities.developer_info.DeveloperInfo
import ir.hasanazimi.me.data.repository.remote.web_services.DeveloperWebServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.inject.Inject

interface DeveloperInfoRepository  {

    suspend fun getDeveloperInfo() : Flow<DeveloperInfo>

    fun getDeveloperInfoByRx() : Observable<DeveloperInfo>

}



class DeveloperInfoRepositoryImpl @Inject constructor(
    private val developerWebServices: DeveloperWebServices,
) : DeveloperInfoRepository {


    override suspend fun getDeveloperInfo(): Flow<DeveloperInfo> = flow {
        emit(developerWebServices.getDeveloperInformation())
    }


    override fun getDeveloperInfoByRx(): Observable<DeveloperInfo> {
        return developerWebServices.getDeveloperInformationByRx().toObservable()
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


