package org.sirekanyan.todo.ext

fun <T> List<T>.print() {
    forEach(::println)
}

fun List<String>.printNumbered() {
    printNumbered(map(String::colorize), start = 1)
}

private fun <T> printNumbered(list: List<T>, start: Int) {
    val padding = (list.lastIndex + start).toString().length
    list.forEachIndexed { index, entry ->
        println((index + start).toString().padStart(padding) + ' ' + entry)
    }
}
