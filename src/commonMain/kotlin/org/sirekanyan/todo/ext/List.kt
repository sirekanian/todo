package org.sirekanyan.todo.ext

fun <T> List<T>.print() {
    forEach(::println)
}

fun <T> List<T>.printNumbered(start: Int = 1) {
    val padding = (lastIndex + start).toString().length
    forEachIndexed { index, entry ->
        println((index + start).toString().padStart(padding) + ' ' + entry)
    }
}
