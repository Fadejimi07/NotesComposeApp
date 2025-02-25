package com.example.notesapp.roomdb

import androidx.room.Database
import androidx.room.Room

@Database(entities = [Note::class], version = 1)
abstract class NoteDb {
    var instance = Room.databaseBuilder(
        context = context.applicationContext,
        NoteDb::class.java,
        "notes_db"
    ).build()
}