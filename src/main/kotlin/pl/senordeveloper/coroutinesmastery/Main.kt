package pl.senordeveloper.coroutinesmastery

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds

suspend fun main() {
    val tracer = TracingCoroutineInterceptor()

    withContext(tracer + CoroutineName("main")) {
        supervisorScope {
            launch(CoroutineName("child-1")) {
                delay(100.milliseconds)
                println("hello")
            }
            launch(CoroutineName("child-2")) {
                delay(200.milliseconds)
            }
            val async1 = async(CoroutineName("child-3")) {
                delay(50.milliseconds)
                "result1"
            }
            val async2 = async(CoroutineName("child-4")) {
                delay(150.milliseconds)
                cancel()
                "result2"
            }
            try {
                awaitAll(async1, async2)
            } catch (_: CancellationException) {
                println("child-4 was cancelled")
            }
        }
    }

    delay(1000.milliseconds)

    tracer.events.forEach { println(it) }

    println(tracer.renderTree())
}
