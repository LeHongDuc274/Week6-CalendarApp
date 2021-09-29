package com.example.calendarapp.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.calendarapp.models.Note
import com.example.calendarapp.repositories.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class NoteViewModel(app: Application) : ViewModel() {

    private val repository = NoteRepository(app)

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deletaAll()
    }

    fun getAllNote(): LiveData<List<Note>>{
        return repository.getAllNote()
    }


    class NoteViewModelFactory(val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                return NoteViewModel(application) as T
            }
            throw IllegalArgumentException("Unable contructor viewmodel")
        }
    }
}