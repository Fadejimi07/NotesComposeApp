package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.roomdb.NoteDb
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

        setContent {
            NotesAppTheme {

                // Display All Records in Room DB
                val notes by viewModel.allNotes.collectAsStateWithLifecycle()

            }
        }
    }
}