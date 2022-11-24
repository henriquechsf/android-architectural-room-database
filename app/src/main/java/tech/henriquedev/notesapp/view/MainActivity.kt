package tech.henriquedev.notesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.henriquedev.notesapp.NoteApplication
import tech.henriquedev.notesapp.R
import tech.henriquedev.notesapp.adapter.NoteAdapter
import tech.henriquedev.notesapp.viewmodel.NoteViewModel
import tech.henriquedev.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)

        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        noteViewModel.myAllNotes.observe(this) { notes ->
            // update UI
            noteAdapter.setNote(notes)
        }
    }
}