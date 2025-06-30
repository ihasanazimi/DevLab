package ir.hasanazimi.devlab.domain

import ir.hasanazimi.devlab.data.repository.sources.XRepository
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