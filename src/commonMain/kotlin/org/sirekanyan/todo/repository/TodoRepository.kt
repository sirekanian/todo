package org.sirekanyan.todo.repository

import kotlinx.io.files.Path
import org.sirekanyan.todo.files.readEntries
import org.sirekanyan.todo.files.writeEntries

interface TodoRepository {

    fun readEntries(): List<String>

    fun writeEntry(entry: String)

    fun removeEntry(index: Int)
}

class TodoRepositoryImpl(
    private val file: Path,
    private val historyRepository: HistoryRepository,
) : TodoRepository {

    override fun readEntries(): List<String> {
        return file.readEntries()
    }

    override fun writeEntry(entry: String) {
        val entries = file.readEntries()
        file.writeEntries(entries.plus(entry))
    }

    override fun removeEntry(index: Int) {
        val entries = file.readEntries().toMutableList()
        if (index in entries.indices) {
            val deletedEntry = entries.removeAt(index)
            historyRepository.writeEntry(deletedEntry)
            file.writeEntries(entries)
        }
    }
}
