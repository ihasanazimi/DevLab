package ir.ha.meproject.model.use_cases

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import kotlinx.coroutines.flow.Flow

interface DeveloperInfoUseCase  {

    suspend fun getDeveloperInfo() : Flow<DeveloperInfo>
    fun getDeveloperInfoByRx() : Observable<DeveloperInfo>

}


