package ir.hasanazimi.me.data.repository.remote.web_services

import retrofit2.Response
import retrofit2.http.GET

interface XWebService{

    @GET("x")
    suspend fun xFunction() : Response<Any>

}