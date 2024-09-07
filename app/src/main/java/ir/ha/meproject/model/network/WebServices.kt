package ir.ha.meproject.model.network

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import retrofit2.http.GET

interface WebServices {

    @GET("24f7eb6b-d4b8-4044-b6e8-028ffd97d8b7")
    suspend fun getDeveloperInformation() : DeveloperInfo


    @GET("24f7eb6b-d4b8-4044-b6e8-028ffd97d8b7")
    fun getDeveloperInformationByRx() : Single<DeveloperInfo>

}