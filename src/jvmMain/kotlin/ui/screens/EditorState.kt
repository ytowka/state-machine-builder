package ui.screens

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import ui.models.graph.GraphView

@Immutable
data class EditorState(
    val editorMode: EditorMode = EditorMode.MOVE,
    val word: String = "",
    val graph: GraphView = GraphView(),
    val capturedVertexIndex: Int? = null
){

}

@Immutable
enum class EditorMode { MOVE, VERTEX,  ARCS}

sealed interface EditorEvent{

}

sealed interface EditorUserEvent : EditorEvent{

    data class ChooseMode(val editorMode: EditorMode) : EditorUserEvent
    data class ChangeWord(val word: String) : EditorUserEvent

    data class AddVertex(val offset: Offset) : EditorUserEvent

    data class MoveVertex(val offset: Offset, val index: Int) : EditorUserEvent

    data class CaptureVertex(val index: Int) : EditorUserEvent
    data class ReleaseVertex(val atIndex: Int?) : EditorUserEvent
}

sealed interface EditorSingleEvent{

}