package ir.ha.meproject.model.network

import io.reactivex.rxjava3.core.Single
import ir.ha.meproject.model.data.fake_data.FakeData
import retrofit2.http.GET
import retrofit2.http.Url

interface OtherWebServices {

    @GET
    fun getDeveloperInformationByRx(@Url url : String) : Single<List<FakeData>>


}