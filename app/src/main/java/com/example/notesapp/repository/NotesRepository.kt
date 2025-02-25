package com.example.notesapp.repository

import com.example.notesapp.roomdb.Note
import com.example.notesapp.roomdb.NoteDao
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()
    suspend fun insertNote(note: Note) = noteDao.insert(note)
}