package pl.senordeveloper.coroutinesmastery

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
        launch(CoroutineName("child-2")) {
            delay(200.milliseconds)
        }
        val async1 = async(CoroutineName("child-3")) {
            delay(50.milliseconds)
            "result1"
        }
        val async2 = async(CoroutineName("child-4")) {
            delay(150.milliseconds)
            "result2"
        }
        awaitAll(async1, async2)
    }

    tracer.events.forEach { println(it) }

    println(tracer.renderTree())

}