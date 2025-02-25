package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.roomdb.Note
import com.example.notesapp.roomdb.NoteDb
import com.example.notesapp.screens.DisplayNotesList
import com.example.notesapp.ui.theme.NotesAppTheme
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Room Db
        val database = NoteDb.getInstance(applicationContext)

        // Repository
        val repository = NotesRepository(database.notesDao)

        // viewmodel factory
        val viewMokdelFactory = NoteViewModelFactory(repository)

        // ViewModel
        val viewModel = ViewModelProvider(
            this, viewMokdelFactory
        )[NoteViewModel::class.java]
        viewModel.getAllNotes()
        val note1 = Note(
            0, "This is a demo note",
            "Welcome my friends, please rate us 5 stars" +
                    "to continue updating this course.",
            "#f59598".toColorInt(),
        )
        viewModel.insert(note1)

        setContent {
            NotesAppTheme {
                // Display All Records in Room DB
                val notes by viewModel.allNotes.collectAsStateWithLifecycle()
                DisplayNotesList(notes)
            }
        }
    }
}