package ir.ha.meproject.model.repositories

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import kotlinx.coroutines.flow.Flow

interface DeveloperInfoRepository  {

    suspend fun getDeveloperInfo() : Flow<DeveloperInfo>

    fun getDeveloperInfoByRx() : Single<DeveloperInfo>


}


