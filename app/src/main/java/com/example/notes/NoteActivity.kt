package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class NoteActivity : AppCompatActivity() {
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        db = DBHelper(this)
        refresh()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    fun deleteNote(@Suppress("UNUSED_PARAMETER") view: View) {
        db.deleteNote(intent.getIntExtra("noteID",0))
        finish()
    }

    fun editNote(@Suppress("UNUSED_PARAMETER") view: View) {
        val id = intent.getIntExtra("noteID", 0)
        val intent = Intent(this, AddOrEditNoteActivity::class.java)
        intent.putExtra("edit", true)
        intent.putExtra("noteID", id)
        startActivity(intent)
    }

    private fun refresh() {
        val note = db.getNote(intent.getIntExtra("noteID", 0))
        val title = findViewById<TextView>(R.id.noteTitle)
        val text = findViewById<TextView>(R.id.noteText)
        if (note != null) {
            title.text = note.title
            text.text = note.content
        }
        text.movementMethod = ScrollingMovementMethod()
    }
}
