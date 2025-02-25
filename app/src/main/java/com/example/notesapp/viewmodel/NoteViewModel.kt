package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.roomdb.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NotesRepository) : ViewModel() {
    private val _allNotes: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())
    val allNotes: StateFlow<List<Note>> = _allNotes.asStateFlow()

    fun getAllNotes() = viewModelScope.launch {
        repository.allNotes.collect { notes ->
            _allNotes.value = notes
        }
    }

    fun insert(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun deleteAllNotes() = viewModelScope.launch {
        repository.deleteAllNotes()
    }
}