package ir.ha.meproject.model.use_cases

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import ir.ha.meproject.model.repositories.DeveloperInfoRepository
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