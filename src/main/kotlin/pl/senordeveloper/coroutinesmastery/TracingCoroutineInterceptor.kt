package pl.senordeveloper.coroutinesmastery

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

class TracingCoroutineInterceptor : ContinuationInterceptor {
    override val key = ContinuationInterceptor.Key
    val events = mutableListOf<CoroutineEvent>()
    val states = LinkedHashMap<Int, CoroutineState>()

    override fun <T> interceptContinuation(
        continuation: Continuation<T>
    ): Continuation<T> = TracingContinuation(continuation, this)

    override fun releaseInterceptedContinuation(continuation: Continuation<*>) {}

    fun renderTree(): String = TreeRenderer.render(states.values.toList())
    fun renderMermaid(): String = MermaidRenderer.render(states.values.toList())
    fun renderMermaidToFile(path: String = "coroutines.mmd") = MermaidRenderer.renderToFile(states.values.toList(), path)
}
