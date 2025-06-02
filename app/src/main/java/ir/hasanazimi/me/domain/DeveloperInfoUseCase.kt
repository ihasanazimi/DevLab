package ir.hasanazimi.me.domain

import io.reactivex.rxjava3.core.Observable
import ir.hasanazimi.me.data.entities.developer_info.DeveloperInfo
import ir.hasanazimi.me.data.repository.sources.DeveloperInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DeveloperInfoUseCase  {

    suspend fun getDeveloperInfo() : Flow<DeveloperInfo>
    fun getDeveloperInfoByRx() : Observable<DeveloperInfo>

}




class DeveloperInfoUseCaseImpl @Inject constructor(
    private val developerInfoRepository: DeveloperInfoRepository,
) : DeveloperInfoUseCase {


    override suspend fun getDeveloperInfo(): Flow<DeveloperInfo> {
        return developerInfoRepository.getDeveloperInfo()
    }

    override fun getDeveloperInfoByRx(): Observable<DeveloperInfo> {
        return developerInfoRepository.getDeveloperInfoByRx()
    }
}

