package dj.dsl.note_black.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase: RoomDatabase(){

    abstract fun getNoteDao(): NoteInterface

    companion object{

        @Volatile
        private var Instance: NoteDatabase? = null
        fun getDatabase(context: Context):NoteDatabase{
            return Instance?: synchronized(this){
                Room
                    .databaseBuilder(context, NoteDatabase::class.java, "notes.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}