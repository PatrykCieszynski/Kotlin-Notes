package com.example.notes

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class NoteClass {
    var id = 0
    var title: String?
    var content: String?
    var date: String = (LocalDateTime.now()).format(formatter)

    internal constructor(title: String?, content: String?) {
        this.title = title
        this.content = content
    }

    internal constructor(id: Int, title: String?, content: String?, date: String) {
        this.id = id
        this.title = title
        this.content = content
        this.date = date
    }

    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
    }
}