package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class AddOrEditNoteActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    private lateinit var noteTitle: EditText
    private lateinit var noteContent: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_edit_note)

        val button = findViewById<Button>(R.id.NoteConfirm)
        db = DBHelper(this)
        noteTitle = findViewById(R.id.editTextNoteTitle)
        noteContent  = findViewById(R.id.editTextNoteContent)
        val edit = intent.getBooleanExtra("edit", false)

        if(edit) {
            val note = db.getNote(intent.getIntExtra("noteID", 0))
            button.text = getString(R.string.edytuj)
            if (note != null) {
                noteTitle.setText(note.title)
                noteContent.setText(note.content)
            }
        }
        else button.text = getString(R.string.dodaj)
    }

    fun decide(@Suppress("UNUSED_PARAMETER") view: View) {
        val edit = intent.getBooleanExtra("edit", false)
        if(edit) editNote()
        else addNote()
    }

    private fun addNote() {
        val newNote = NoteClass(noteTitle.text.toString(), noteContent.text.toString())
        db.addNote(newNote)
        setResult(RESULT_OK, Intent())
        finish()
    }

    private fun editNote() {
        val note = db.getNote(intent.getIntExtra("noteID", 0))
        if (note != null) {
            note.title = noteTitle.text.toString()
            note.content = noteContent.text.toString()
            db.updateNote(note)
        }
        setResult(RESULT_OK, Intent())
        finish()
    }
}