package tech.henriquedev.notesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tech.henriquedev.notesapp.R

class NoteAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)
    }
}