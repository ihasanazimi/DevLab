package ir.ha.meproject.model.network

import io.reactivex.rxjava3.core.Single
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import retrofit2.http.GET

interface WebServices {

    @GET("8b57a530-daac-48e8-a54b-1373b3aa3b39")
    suspend fun getDeveloperInformation() : DeveloperInfo


    @GET("8b57a530-daac-48e8-a54b-1373b3aa3b39")
    fun getDeveloperInformationByRx() : Single<DeveloperInfo>

}