package org.sirekanyan.todo.time

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

val Today: String by lazy {
    @OptIn(ExperimentalTime::class)
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    now.date.format(LocalDate.Formats.ISO)
}
