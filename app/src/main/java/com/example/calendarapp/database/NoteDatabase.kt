package com.example.calendarapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.calendarapp.database.Dao.NoteDao
import com.example.calendarapp.models.Converters
import com.example.calendarapp.models.Note

@Database(entities = [Note::class],version = 1)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object{
        @Volatile
        private var instance : NoteDatabase? = null

        fun getInstance(context : Context): NoteDatabase{
            if(instance == null){
                instance = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                     "noteDatabase"
                ).build()
            }
            return instance!!
        }
    }
}