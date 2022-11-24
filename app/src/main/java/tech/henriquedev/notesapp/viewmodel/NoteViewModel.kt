package tech.henriquedev.notesapp.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.henriquedev.notesapp.model.Note
import tech.henriquedev.notesapp.repository.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val myAllNotes: LiveData<List<Note>> = repository.myAllNotes.asLiveData()

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun upate(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }
}

class NoteViewModelFactory(private var repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(repository) as T
        } else {
            throw IllegalArgumentException("unknown ViewModel")
        }
    }
}