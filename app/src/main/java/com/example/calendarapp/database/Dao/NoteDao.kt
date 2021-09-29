package com.example.calendarapp.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.calendarapp.models.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("select * from note_table order by date ASC")
    fun getAllNote(): LiveData<List<Note>>

    @Query("delete from note_table")
    suspend fun deleteAll()
    @RawQuery
    fun insertDataRawFormat(query: SupportSQLiteQuery): Boolean?
}