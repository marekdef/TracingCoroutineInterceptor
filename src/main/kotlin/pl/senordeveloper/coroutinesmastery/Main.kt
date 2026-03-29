package pl.senordeveloper.coroutinesmastery

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val tracer = TracingCoroutineInterceptor()

    runBlocking(tracer + CoroutineName("main")) {
        launch(CoroutineName("child-1")) {
            delay(100.milliseconds)
            println("hello")
        }
    }

    tracer.events.forEach { println(it) }
}