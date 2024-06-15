package ir.ha.meproject.model.repositories

import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import ir.ha.meproject.model.network.WebServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeveloperInfoRepositoryImpl @Inject constructor(
    private val webServices: WebServices,
) : DeveloperInfoRepository {


    override suspend fun getDeveloperInfo(): Flow<DeveloperInfo> = flow {
        emit(webServices.getDeveloperInformation())
    }
}