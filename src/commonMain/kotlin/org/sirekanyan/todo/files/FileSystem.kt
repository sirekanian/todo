package org.sirekanyan.todo.files

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import platform.posix.getenv

val CurrentFileSystem = SystemFileSystem

private val HomeDirectory: Path =
    @OptIn(ExperimentalForeignApi::class)
    checkNotNull(getenv("HOME")?.toKString()?.let(::Path)) {
        "The HOME environment variable is not specified"
    }

val TodoFile: Path =
    Path(HomeDirectory, "todo.txt")

val HistoryFile: Path =
    Path(HomeDirectory, ".todo_history")
