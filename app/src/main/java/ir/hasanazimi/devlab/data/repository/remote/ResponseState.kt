package ir.hasanazimi.devlab.data.repository.remote

import androidx.annotation.Keep

@Keep
sealed class ResponseState<out T>(
    val data: T? = null,
    val exception: Exception? = null
) {
    @Keep
    class Success<T>(data: T? = null) : ResponseState<T>(data)
    @Keep
    class Error<T>(exception: Exception, data: T? = null) : ResponseState<T>(data, exception)
    @Keep
    object Loading : ResponseState<Nothing>()
}