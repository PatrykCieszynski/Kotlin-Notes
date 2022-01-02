package com.example.notes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var db : DBHelper
    private lateinit var adapter: NoteAdapter
    private lateinit var allNotes: ArrayList<NoteClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notesView = findViewById<RecyclerView>(R.id.notesView)
        notesView.layoutManager = LinearLayoutManager(this)
        db = DBHelper(this)
        allNotes = db.getNotes()
        adapter = NoteAdapter({position -> onListItemClick(position)}, allNotes)
        notesView.visibility = View.VISIBLE
        notesView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun onListItemClick(position: Int) {
        val intent = Intent(this, NoteActivity::class.java)
        val note = allNotes[position]
        intent.putExtra("noteID", note.id)
        startActivity(intent)
    }

    fun addNote(@Suppress("UNUSED_PARAMETER") view: View) {
        startActivity(Intent(this,AddOrEditNoteActivity::class.java))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refresh() {
        allNotes.clear()
        allNotes.addAll(db.getNotes())
        adapter.notifyDataSetChanged()
    }
}