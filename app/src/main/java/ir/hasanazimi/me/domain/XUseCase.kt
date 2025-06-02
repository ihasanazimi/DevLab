package ir.hasanazimi.me.domain

import ir.hasanazimi.me.data.repository.sources.XRepository
import javax.inject.Inject


interface XUseCase {
    fun xFunction() : Nothing
}


class XUseCaseImpl @Inject constructor(
    private val xRepository: XRepository
) : XUseCase{

    override fun xFunction(): Nothing {
        TODO("Not yet implemented")
    }
}