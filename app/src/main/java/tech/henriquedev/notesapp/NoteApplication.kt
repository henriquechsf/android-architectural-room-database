package tech.henriquedev.notesapp

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import tech.henriquedev.notesapp.repository.NoteRepository
import tech.henriquedev.notesapp.room.NoteDatabase

class NoteApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { NoteDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NoteRepository(database.getNoteDao()) }
}