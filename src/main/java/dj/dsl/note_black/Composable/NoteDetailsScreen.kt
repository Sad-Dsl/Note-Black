package dj.dsl.note_black.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dj.dsl.note_black.R
import dj.dsl.note_black.database.NoteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsScreen(
    note: NoteEntity,
    onNavigationBack: ()->Unit,
    onUpdateNote : (NoteEntity)->Unit
){
    var title by rememberSaveable { mutableStateOf(note.title) }
    var content by rememberSaveable { mutableStateOf(note.content) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {onNavigationBack()}
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "null"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onUpdateNote(
                                NoteEntity(
                                    id = note.id,
                                    title = title,
                                    content = content
                            )
                            )
                            onNavigationBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "null"//stringResource(R.string.mettre_a_jour)
                        )
                    }
                }
            )
        }
    ) {paddingValues ->

        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)) {
            NoteBlackText(
                value = title,
                onValueChange = {
                    title = it
                },
                textStyle = MaterialTheme.typography.titleLarge,
                placeholder = {
                    Text(text = stringResource(R.string.titre))
                },
                modifier = Modifier.fillMaxWidth()
            )
            NoteBlackText(
                value = content,
                onValueChange = {
                    content = it
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(text = stringResource(R.string.content))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun NoteBlackText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange : (String)->Unit,
    textStyle: TextStyle,
    placeholder: @Composable (()->Unit)
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        placeholder = placeholder,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun NoteDetailsScreenPreview()
{
}