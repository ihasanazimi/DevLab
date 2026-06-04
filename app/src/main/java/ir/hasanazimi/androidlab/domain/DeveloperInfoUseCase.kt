package ir.hasanazimi.androidlab.domain

import io.reactivex.rxjava3.core.Observable
import ir.hasanazimi.androidlab.data.entities.developer_info.DeveloperInfo
import ir.hasanazimi.androidlab.data.repository.sources.DeveloperInfoRepository
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

