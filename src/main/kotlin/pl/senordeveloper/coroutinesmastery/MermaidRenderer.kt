package pl.senordeveloper.coroutinesmastery

import java.io.File

object MermaidRenderer {

    fun render(states: List<CoroutineState>): String {
        val byId = states.associateBy { it.id }
        val sb = StringBuilder()
        sb.appendLine("flowchart TD")

        states.forEach { state ->
            val label = buildList {
                add(state.name)
                add("resumed: ${state.resumeCount}x")
                if (state.totalSuspendedMs > 0) add("suspended: ${state.totalSuspendedMs}ms")
                add(state.status.label)
            }.joinToString("<br/>")
            sb.appendLine("""    ${nodeId(state.id)}["$label"]""")
        }

        states.forEach { state ->
            if (state.parentId != null && state.parentId in byId) {
                sb.appendLine("    ${nodeId(state.parentId)} --> ${nodeId(state.id)}")
            }
        }

        states.forEach { state ->
            val color = when (state.status) {
                CoroutineStatus.COMPLETED -> "fill:#90EE90,stroke:#2d862d"
                CoroutineStatus.CANCELLED -> "fill:#FFB6C1,stroke:#cc0000"
                CoroutineStatus.FAILED    -> "fill:#FF6B6B,stroke:#cc0000"
                CoroutineStatus.ACTIVE    -> "fill:#ADD8E6,stroke:#00008B"
            }
            sb.appendLine("    style ${nodeId(state.id)} $color")
        }

        return sb.toString().trimEnd()
    }

    fun renderToFile(states: List<CoroutineState>, path: String = "coroutines.mmd"): File {
        val file = File(path)
        file.writeText(render(states))
        return file
    }

    private fun nodeId(id: Int) = "n${Integer.toHexString(id)}"
}
