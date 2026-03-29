package pl.senordeveloper.coroutinesmastery

object TreeRenderer {

    fun render(states: List<CoroutineState>): String {
        val byId = states.associateBy { it.id }
        val children = states.groupBy { it.parentId }
        val roots = states.filter { it.parentId == null || it.parentId !in byId }

        val sb = StringBuilder()
        roots.forEach { root ->
            renderNode(root, children, prefix = "", isLast = true, sb = sb, isRoot = true)
        }
        return sb.toString().trimEnd()
    }

    private fun renderNode(
        state: CoroutineState,
        children: Map<Int?, List<CoroutineState>>,
        prefix: String,
        isLast: Boolean,
        sb: StringBuilder,
        isRoot: Boolean = false
    ) {
        val connector = when {
            isRoot -> ""
            isLast -> "└── "
            else -> "├── "
        }
        sb.appendLine("$prefix$connector${label(state)}")

        val childPrefix = when {
            isRoot -> ""
            isLast -> "$prefix    "
            else -> "$prefix│   "
        }
        val childList = children[state.id] ?: emptyList()
        childList.forEachIndexed { i, child ->
            renderNode(child, children, childPrefix, i == childList.lastIndex, sb)
        }
    }

    private fun label(state: CoroutineState): String {
        val details = buildList {
            if (state.resumeCount > 0) add("resumed ${state.resumeCount}x")
            if (state.totalSuspendedMs > 0) add("total suspended: ${state.totalSuspendedMs}ms")
            add(state.status.label)
        }
        return if (details.isEmpty()) state.name
        else "${state.name} [${details.joinToString(", ")}]"
    }
}
