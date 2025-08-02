import com.github.ajalt.clikt.core.CoreCliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import org.sirekanyan.todo.files.HistoryFile
import org.sirekanyan.todo.files.TodoFile
import org.sirekanyan.todo.ext.print
import org.sirekanyan.todo.ext.printNumbered
import org.sirekanyan.todo.repository.HistoryRepository
import org.sirekanyan.todo.repository.HistoryRepositoryImpl
import org.sirekanyan.todo.repository.TodoRepository
import org.sirekanyan.todo.repository.TodoRepositoryImpl

private val todoRepository: TodoRepository by lazy { TodoRepositoryImpl(TodoFile, historyRepository) }
private val historyRepository: HistoryRepository by lazy { HistoryRepositoryImpl(HistoryFile) }

fun main(args: Array<String>) {
    MainCommand().main(args)
}

private class MainCommand : CoreCliktCommand("todo") {

    private val entry by argument().multiple()
    private val delete: Int? by option("-d", "--delete", metavar = "ℕ").int().help("Delete ℕ-th entry")
    private val history: Boolean by option("-h", "--history").flag().help("Show deleted entries")

    override fun run() {
        val entry = entry.ifEmpty { null }?.joinToString(" ")
        val delete = delete
        when {
            entry == null && delete == null && !history -> {
                todoRepository.readEntries().printNumbered()
            }
            entry != null && delete == null && !history -> {
                todoRepository.writeEntry(entry)
                todoRepository.readEntries().printNumbered()
            }
            entry == null && delete != null && !history -> {
                todoRepository.removeEntry(delete - 1)
                todoRepository.readEntries().printNumbered()
            }
            entry == null && delete == null && history -> {
                historyRepository.readEntries().print()
            }
            else -> {
                echoFormattedHelp()
            }
        }
    }
}
