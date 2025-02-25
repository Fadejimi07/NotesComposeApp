package com.example.notesapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.notesapp.roomdb.Note
import com.example.notesapp.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayDialog(viewModel: NoteViewModel) {
    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var selectedColor by remember {
        mutableStateOf(Color.Blue)
    }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = {
                val note = Note(0, title, description, selectedColor.toArgb())

                viewModel.insert(note)
            }) {
                Text(text = "Save Note")
            }
        },
        dismissButton = {
            Button(onClick = { }) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Enter Note") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Note title") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Note Description") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Color picker composable
            }
        }
    )
}