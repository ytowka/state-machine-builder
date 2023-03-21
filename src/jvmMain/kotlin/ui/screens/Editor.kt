package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui.graph.Graph
import ui.kit.Card

@Composable
fun Editor(
    viewModel: EditorViewModel,
){
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Graph(
            modifier = Modifier.fillMaxSize()
        )
        Toolbox(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterStart),
            editorMode = state.editorMode,
            onEditorModeClicked = {
                viewModel.processUserEvent(EditorUserEvent.ChooseMode(it))
            }
        )
        WordField(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopCenter),
            word = state.word,
            onWordChange = {
                viewModel.processUserEvent(EditorUserEvent.ChangeWord(it))
            }
        )
    }
}

@Composable
fun WordField(
    modifier: Modifier = Modifier,
    word: String,
    onWordChange: (String) -> Unit,
){
    Card(
        modifier = modifier
    ) {
        Box(
            Modifier
                .padding(8.dp)
                .border(width = 2.dp, color = MaterialTheme.colors.primary, shape = RoundedCornerShape(4.dp))
                .height(48.dp)
                .width(300.dp)
        ){
            BasicTextField(
                modifier = modifier
                    .align(Alignment.CenterStart)
                    .fillMaxSize()
                ,
                value = word,
                onValueChange = onWordChange,
                singleLine = true,
            )
        }
    }
}

@Composable
fun Toolbox(
    modifier: Modifier = Modifier,
    editorMode: EditorMode,
    onEditorModeClicked: (EditorMode) -> Unit,
){
    Card(
        modifier = modifier
    ){
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EditorMode.values().forEach {
                val isSelected = it == editorMode
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true)
                        ) {
                            onEditorModeClicked(it)
                        }
                        .background(color = if(isSelected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.surface, shape = RoundedCornerShape(4.dp))
                        .size(48.dp),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = when(it){
                            EditorMode.MOVE -> painterResource("ic_open_with.svg")
                            EditorMode.ARCS -> rememberVectorPainter(Icons.Rounded.Share)
                            EditorMode.VERTEX -> painterResource("ic_vertex.svg")
                        },
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
