package ir.ha.mylibrary.data.remote

import ir.ha.mylibrary.data.model.Users
import retrofit2.http.GET

interface UserWebService{

    /**   https://mocki.io/v1/    */
    @GET("36693b79-b924-4287-8c5e-737e6eb8319e")
    suspend fun getUsersWebService() : Users

}