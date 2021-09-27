package com.example.calendarapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.calendarapp.models.Note
import com.example.calendarapp.repositories.NoteRepository
import kotlinx.coroutines.launch
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

    fun getAllNote(): LiveData<List<Note>> = repository.getAllNote()

    class NoteViewModelFactory(val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                return NoteViewModel(application) as T
            }
            throw IllegalArgumentException("Unable contructor viewmodel")
        }
    }
}