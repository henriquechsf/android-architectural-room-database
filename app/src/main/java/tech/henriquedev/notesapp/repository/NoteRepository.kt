package tech.henriquedev.notesapp.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import tech.henriquedev.notesapp.model.Note
import tech.henriquedev.notesapp.room.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    val myAllNotes: Flow<List<Note>> = noteDao.getAllNotes()

    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    @WorkerThread
    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    @WorkerThread
    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    @WorkerThread
    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}