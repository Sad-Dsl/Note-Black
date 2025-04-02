package dj.dsl.note_black.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteInterface {

    @Upsert
    suspend fun updateNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNotes(notes: List<NoteEntity>)

    @Query("SELECT * FROM noteentity")
    fun getNotes(): Flow<List<NoteEntity>>
}