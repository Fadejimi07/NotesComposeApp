package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.notesapp.repository.NotesRepository
import com.example.notesapp.roomdb.NoteDb
import com.example.notesapp.screens.DisplayDialog
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
        val viewModelFactory = NoteViewModelFactory(repository)

        // ViewModel
        val viewModel = ViewModelProvider(
            this, viewModelFactory
        )[NoteViewModel::class.java]
        viewModel.getAllNotes()

        setContent {
            NotesAppTheme {
                val notes by viewModel.allNotes.collectAsStateWithLifecycle()
                // Display All Records in Room DB
                Scaffold(
                    floatingActionButton = { MyFAB(viewModel) }
                ) { innerPadding ->
                    DisplayNotesList(notes = notes, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MyFAB(viewModel: NoteViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    DisplayDialog(
        viewModel = viewModel,
        showDialog = showDialog,
    ) {
        showDialog = false
    }
    FloatingActionButton(
        onClick = { showDialog = true },
        containerColor = Color.Blue,
        contentColor = Color.White
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_add_24),
            contentDescription = "Add Note"
        )
    }
}