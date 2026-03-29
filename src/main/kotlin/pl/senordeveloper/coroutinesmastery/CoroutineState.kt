package pl.senordeveloper.coroutinesmastery

class CoroutineState(
    val id: Int,
    val parentId: Int?,
    val name: String
) {
    var resumeCount: Int = 0
    var totalSuspendedMs: Long = 0
    var status: CoroutineStatus = CoroutineStatus.ACTIVE
    internal var lastResumeEndTimestamp: Long = 0
}
