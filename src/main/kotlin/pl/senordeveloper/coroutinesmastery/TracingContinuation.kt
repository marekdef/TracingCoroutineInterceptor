package pl.senordeveloper.coroutinesmastery

import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job

@OptIn(ExperimentalCoroutinesApi::class)
class TracingContinuation<T>(
    private val delegate: Continuation<T>,
    private val interceptor: TracingCoroutineInterceptor
) : Continuation<T> {

    override val context: CoroutineContext = delegate.context

    override fun resumeWith(result: Result<T>) {
        val job = context[Job]
        val id = System.identityHashCode(job)
        val parentId = job?.parent?.let { System.identityHashCode(it) }
        val name = context[CoroutineName]?.name ?: "unnamed"
        val now = System.currentTimeMillis()

        val state = interceptor.states.getOrPut(id) { CoroutineState(id, parentId, name) }
        if (state.lastResumeEndTimestamp > 0) {
            state.totalSuspendedMs += now - state.lastResumeEndTimestamp
        }
        state.resumeCount++

        interceptor.events += CoroutineEvent(
            id = id,
            parentId = parentId,
            coroutineName = name,
            thread = Thread.currentThread().name,
            timestamp = now,
            result = result
        )

        delegate.resumeWith(result)

        state.lastResumeEndTimestamp = System.currentTimeMillis()
        if (job?.isCompleted == true) {
            state.isCompleted = true
        }
    }
}
