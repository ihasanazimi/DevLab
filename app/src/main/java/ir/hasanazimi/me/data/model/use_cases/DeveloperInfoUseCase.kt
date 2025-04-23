package ir.hasanazimi.me.data.model.use_cases

import io.reactivex.rxjava3.core.Observable
import ir.hasanazimi.me.data.model.data.developer_info.DeveloperInfo
import kotlinx.coroutines.flow.Flow

interface DeveloperInfoUseCase  {

    suspend fun getDeveloperInfo() : Flow<DeveloperInfo>
    fun getDeveloperInfoByRx() : Observable<DeveloperInfo>

}


