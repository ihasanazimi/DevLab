package ir.ha.meproject.model.use_cases

import io.reactivex.rxjava3.core.Observable
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import ir.ha.meproject.model.repositories.developer_info.DeveloperInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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