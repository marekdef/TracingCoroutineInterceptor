package pl.senordeveloper.coroutinesmastery

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

class TracingCoroutineInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor.Key
    val events = mutableListOf<CoroutineEvent>()

    override fun <T> interceptContinuation(
        continuation: Continuation<T>
    ): Continuation<T> = TracingContinuation(continuation, this)

    override fun releaseInterceptedContinuation(continuation: Continuation<*>) {}
}