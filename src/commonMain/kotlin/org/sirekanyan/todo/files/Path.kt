package org.sirekanyan.todo.files

import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.readString
import kotlinx.io.writeString

private const val LineSeparator = "\n"

fun Path.readEntries(): List<String> {
    if (!CurrentFileSystem.exists(this)) return listOf()
    val content = CurrentFileSystem.source(this).buffered().use(Source::readString)
    return content.split(LineSeparator).filter(String::isNotBlank)
}

fun Path.writeEntries(entries: List<String>) {
    val content = entries.filter(String::isNotBlank).joinToString(LineSeparator, postfix = LineSeparator)
    CurrentFileSystem.sink(this).buffered().use { sink -> sink.writeString(content) }
}
