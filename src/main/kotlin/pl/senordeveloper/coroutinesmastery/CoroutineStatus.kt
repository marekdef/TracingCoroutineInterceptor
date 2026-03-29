package pl.senordeveloper.coroutinesmastery

enum class CoroutineStatus(val label: String) {
    ACTIVE("active"),
    COMPLETED("completed ✓"),
    CANCELLED("cancelled ✗"),
    FAILED("failed ✗")
}
