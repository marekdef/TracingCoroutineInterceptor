package pl.senordeveloper.coroutinesmastery

data class CoroutineEvent(
    val coroutineName: String,
    val thread: String,
    val timestamp: Long,
    val result: Result<*>
)