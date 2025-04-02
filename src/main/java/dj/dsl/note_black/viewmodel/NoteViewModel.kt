package dj.dsl.note_black.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dj.dsl.note_black.database.NoteDatabase
import dj.dsl.note_black.database.NoteEntity
import kotlinx.coroutines.launch

class NoteViewModel(context: Application) : AndroidViewModel(context) {

     val dao = NoteDatabase.getDatabase(context).getNoteDao()

    val selectedNotes = mutableStateListOf<NoteEntity>()

    fun updateNote(note: NoteEntity){
        viewModelScope.launch {
            dao.updateNote(note)
        }
    }
    fun deleteNote(){
        viewModelScope.launch {
            dao.deleteNotes(selectedNotes)
        }
    }

    fun getData() = dao.getNotes()

    fun onChangeSelection(note: NoteEntity){
        if(selectedNotes.contains(note)){
            selectedNotes.remove(note)
        }else{
            selectedNotes.add(note)
        }
    }

    fun clearSelection(){
        selectedNotes.clear()
    }
}