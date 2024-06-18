package ir.ha.meproject.model.network

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import retrofit2.http.GET

interface WebServices {

    @GET("536247ec-6752-495b-b43d-d6e68e2e225d")
    suspend fun getDeveloperInformation() : DeveloperInfo


    @GET("536247ec-6752-495b-b43d-d6e68e2e225d")
    fun getDeveloperInformationByRx() : Single<DeveloperInfo>

}