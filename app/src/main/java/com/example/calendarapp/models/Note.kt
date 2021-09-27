package com.example.calendarapp.models

import androidx.room.*
import java.util.*

@Entity(tableName = "note_table")
class Note(
    @ColumnInfo(name = "content") var content: String,
    @TypeConverters(Converters::class)
    var date: Date? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var id: Int = 0
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
