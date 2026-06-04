package ir.hasanazimi.androidlab.domain

import ir.hasanazimi.androidlab.data.repository.sources.XRepository
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