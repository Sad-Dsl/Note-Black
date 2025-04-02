package dj.dsl.note_black

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dj.dsl.note_black.Composable.ListNotesScreen
import dj.dsl.note_black.Composable.NoteDetailsScreen
import dj.dsl.note_black.database.NoteEntity
import dj.dsl.note_black.ui.theme.NoteBlackTheme
import dj.dsl.note_black.viewmodel.NoteViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteBlackTheme {

                val viewModel by viewModels<NoteViewModel>()
                val notes  = viewModel.getData().collectAsStateWithLifecycle(emptyList())
                val navController = rememberNavController()
                println("infos ${notes.value}")

                NavHost(
                    navController = navController,
                    startDestination = ListNotes
                )
                {
                    composable<ListNotes>{
                        ListNotesScreen(
                            notes = notes.value,
                            selectedNotes = viewModel.selectedNotes,
                            onDeleteNote = {
                                viewModel.deleteNote()
                            },
                            onClickAddNote = {
                                navController.navigate(DetailsNotes())
                            },
                            onChangeSelection = {newNote->
                                viewModel.onChangeSelection(newNote)
                            },
                            onNavigateNoteDetailsScreen = {note->
                                navController.navigate(
                                    DetailsNotes(
                                        id = note.id,
                                        title = note.title,
                                        content = note.content
                                    )
                                )
                            },
                            onClearSelection = {
                                viewModel.clearSelection()
                            }
                        )
                    }
                    composable<DetailsNotes>{
                        val args = it.toRoute<DetailsNotes>()
                        val note = NoteEntity(
                            id = args.id,
                            title = args.title,
                            content = args.content
                        )
                        NoteDetailsScreen(
                            note = note,
                            onNavigationBack = {
                                navController.popBackStack()
                            },
                            onUpdateNote = {noteEntity ->
                                viewModel.updateNote(noteEntity)
                            }
                        )
                    }
                }
            }
        }
    }
}
/*@Composable
fun Navigation(viewModel: NoteViewModel){
    val navController = rememberNavController()
    val notes = viewModel.getNotes().collectAsStateWithLifecycle(emptyList())
    NavHost(navController = navController, startDestination = "ListNotesScreen"){
        composable<ListNotesScreen> {
            ListNotesScreen(   notes = notes.value,
                selectedNotes = viewModel.selectedNotes,
                onDeleteNote = {
                    viewModel.deleteNote()
                },
                onClickAddNote = {
                    navController.navigate(DetailsNotes())
                },
                onChangeSelection = {note->
                    viewModel.onChangeSelection(note)
                },
                onNavigateNoteDetailsScreen = {note->
                    navController.navigate(
                        DetailsNotes(
                            id = note.id,
                            title = note.title,
                            content = note.content
                        )
                    )
                },
                onClearSelection = {
                    viewModel.clearSelection()
                }
            )
        }
        composable<DetailsScreen>{
            val args = it.toRoute<DetailsNotes>()
            val note = NoteEntity(
                id = args.id,
                title = args.title,
                content = args.content
            )
            NoteDetailsScreen(
                note = note,
                onNavigationBack = {
                    navController.popBackStack()
                },
                onUpdateNote = { note ->
                    viewModel.updateNote(note)
                },
            )
        }
    }
}
*/

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NoteBlackTheme {
    }
}
@Serializable
object ListNotes

@Serializable
data class DetailsNotes(val id: Int? = null, val title: String ="", val content : String ="")