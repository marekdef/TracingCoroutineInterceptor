package pl.senordeveloper.coroutinesmastery

data class CoroutineEvent(
    val id: Int,
    val parentId: Int?,
    val coroutineName: String,
    val thread: String,
    val timestamp: Long,
    val result: Result<*>
)
