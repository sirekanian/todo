package org.sirekanyan.todo.ext

private val priorityRegex = Regex("\\([A-Za-z]\\)")

fun String.colorize(): String {
    val color = calculateColor(this) ?: return this
    return "\u001b[${color.code};1m${this}\u001b[0m"
}

private fun calculateColor(entry: String): Color? {
    val priority = priorityRegex.findAll(entry)
        .map(MatchResult::value)
        .map(String::uppercase)
        .minOrNull()
    return when (priority) {
        null -> null
        "(A)" -> Color.Red
        "(B)" -> Color.Yellow
        "(C)" -> Color.Green
        else -> Color.Blue
    }
}

private enum class Color(val code: Int) {
    Red(31), Yellow(33), Green(32), Blue(34);
}
