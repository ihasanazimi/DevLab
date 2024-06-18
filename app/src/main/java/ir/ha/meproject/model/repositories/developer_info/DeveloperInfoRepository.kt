package ir.ha.meproject.model.repositories.developer_info

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import kotlinx.coroutines.flow.Flow

interface DeveloperInfoRepository  {

    suspend fun getDeveloperInfo() : Flow<DeveloperInfo>

    fun getDeveloperInfoByRx() : Observable<DeveloperInfo>


}


