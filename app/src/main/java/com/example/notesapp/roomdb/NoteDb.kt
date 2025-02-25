package com.example.notesapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDb : RoomDatabase() {
    abstract val notesDao: NoteDao


    companion object {

        @Volatile // Volatie prevents any possible race conditions in multithreading situations
        private var INSTANCE: NoteDb? = null

        fun getInstance(context: Context): NoteDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context = context.applicationContext,
                        NoteDb::class.java,
                        "notes_db"
                    ).build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}