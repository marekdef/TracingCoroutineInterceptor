package pl.senordeveloper.coroutinesmastery

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineName

class TracingContinuation<T>(
    private val delegate: Continuation<T>,
    private val interceptor: TracingCoroutineInterceptor
) : Continuation<T> {

    override val context: CoroutineContext = delegate.context

    override fun resumeWith(result: Result<T>) {
        interceptor.events += CoroutineEvent(
            coroutineName = context[CoroutineName]?.name ?: "unnamed",
            thread = Thread.currentThread().name,
            timestamp = System.currentTimeMillis(),
            result = result
        )
        delegate.resumeWith(result)
    }
}
