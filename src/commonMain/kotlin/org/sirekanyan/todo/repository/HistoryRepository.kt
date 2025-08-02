package org.sirekanyan.todo.repository

import kotlinx.io.files.Path
import org.sirekanyan.todo.files.readEntries
import org.sirekanyan.todo.files.writeEntries
import org.sirekanyan.todo.time.Today

interface HistoryRepository {

    fun readEntries(): List<String>

    fun writeEntry(entry: String)
}

class HistoryRepositoryImpl(
    private val file: Path,
) : HistoryRepository {

    override fun readEntries(): List<String> {
        return file.readEntries()
    }

    override fun writeEntry(entry: String) {
        val entries = file.readEntries()
        file.writeEntries(entries.plus("$Today $entry"))
    }
}
