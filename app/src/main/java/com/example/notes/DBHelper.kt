package com.example.notes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE "
                + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, "
                + TITLE_COL + " TEXT,"
                + CONTENT_COL + " TEXT,"
                + DATE_COL + " TEXT"
                + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getNotes(): ArrayList<NoteClass> {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val storedNotes = ArrayList<NoteClass>()
        val cursor = db.rawQuery(query,null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(0).toInt()
                val title = cursor.getString(1)
                val content = cursor.getString(2)
                val date = cursor.getString(3)
                storedNotes.add(NoteClass(id, title, content, date))
            } while(cursor.moveToNext())
        }
        cursor.close()
        return storedNotes
    }

    fun getNote(id: Int): NoteClass? {
        val query = "SELECT * FROM $TABLE_NAME where $ID_COL = $id"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query,null)
        return if (cursor.moveToFirst()) {
            val title = cursor.getString(1)
            val content = cursor.getString(2)
            val date = cursor.getString(3)
            val storedNote = (NoteClass(id,title,content,date))
            cursor.close()
            storedNote
        } else
            return null
    }

    fun addNote(note : NoteClass){
        val values = ContentValues()

        values.put(TITLE_COL, note.title)
        values.put(CONTENT_COL, note.content)
        values.put(DATE_COL, note.date)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun updateNote(note: NoteClass) {
        val values = ContentValues()
        values.put(TITLE_COL, note.title)
        values.put(CONTENT_COL, note.content)
        val db = this.writableDatabase
        db.update(
            TABLE_NAME,
            values,
            "$ID_COL = ?",
            arrayOf(note.id.toString())
        )
    }
    fun deleteNote(id: Int) {
        val db = this.writableDatabase
        db.delete(
            TABLE_NAME,
            "$ID_COL = ?",
            arrayOf(id.toString())
        )
    }

    companion object{
        private const val DATABASE_NAME = "NotesDB"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "notes"
        const val ID_COL = "id"
        const val TITLE_COL = "title"
        const val CONTENT_COL = "content"
        const val DATE_COL = "date"
    }
}