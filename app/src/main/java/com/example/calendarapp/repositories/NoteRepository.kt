package com.example.calendarapp.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.calendarapp.database.Dao.NoteDao
import com.example.calendarapp.database.NoteDatabase
import com.example.calendarapp.models.Note

class NoteRepository(app: Application) {

    private val noteDao: NoteDao

    init {
        val noteDatabase = NoteDatabase.getInstance(app)
        noteDao = noteDatabase.getNoteDao()
    }

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun deleteNote(note:Note) = noteDao.deleteNote(note)
    suspend fun updateNote(note:Note) = noteDao.updateNote(note)
    suspend fun deletaAll() = noteDao.deleteAll()

    fun getAllNote() : LiveData<List<Note>> = noteDao.getAllNote()
}