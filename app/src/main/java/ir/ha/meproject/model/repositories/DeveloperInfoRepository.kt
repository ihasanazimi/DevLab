package ir.ha.meproject.model.repositories

import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import kotlinx.coroutines.flow.Flow

interface DeveloperInfoRepository  {

    suspend fun getDeveloperInfo() : Flow<DeveloperInfo>

}


