package tech.henriquedev.notesapp.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.henriquedev.notesapp.NoteApplication
import tech.henriquedev.notesapp.R
import tech.henriquedev.notesapp.adapter.NoteAdapter
import tech.henriquedev.notesapp.model.Note
import tech.henriquedev.notesapp.viewmodel.NoteViewModel
import tech.henriquedev.notesapp.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter

        // register activity for result
        registerActivityResultLauncher()

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)

        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        noteViewModel.myAllNotes.observe(this) { notes ->
            // update UI
            noteAdapter.setNote(notes)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)
    }

    fun registerActivityResultLauncher() {
        addActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultAddNote ->
            val resultCode = resultAddNote.resultCode
            val data = resultAddNote.data

            if (resultCode == RESULT_OK && data != null) {
                val noteTitle: String = data.getStringExtra("title").toString()
                val noteDescription: String = data.getStringExtra("description").toString()

                val note = Note(noteTitle, noteDescription)
                noteViewModel.insert(note)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_note -> {
                val intent = Intent(this, NoteAddActivity::class.java)
                addActivityResultLauncher.launch(intent)
            }
            R.id.menu_delete_all_notes -> showDialogMessage()
        }
        return true
    }

    private fun showDialogMessage() {
        val dialogMessage = AlertDialog.Builder(this)
            .setTitle("Delete All Notes")
            .setMessage("If click yes all notes will delete, if you want delete a specific note, please swipe left or right")
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
            .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ -> noteViewModel.deleteAllNotes() })
            .create()
            .show()

    }
}