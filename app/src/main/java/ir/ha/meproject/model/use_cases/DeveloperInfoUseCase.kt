package ir.ha.meproject.model.use_cases

import ir.ha.meproject.model.data.developer_info.DeveloperInfo
import kotlinx.coroutines.flow.Flow

interface DeveloperInfoUseCase  {

    suspend fun getDeveloperInfo() : Flow<DeveloperInfo>

}


