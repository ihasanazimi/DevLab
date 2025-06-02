package ir.hasanazimi.me.data.repository.sources

import ir.hasanazimi.me.data.repository.remote.web_services.XWebService
import javax.inject.Inject

interface XRepository {
    fun xFunction() : Nothing
}


class XRepositoryImpl @Inject constructor(
    private val xWebService: XWebService
) : XRepository{

    override fun xFunction(): Nothing {
        TODO("Not yet implemented")
    }
}