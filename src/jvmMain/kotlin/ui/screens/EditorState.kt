package ui.screens
data class EditorState(
    val editorMode: EditorMode = EditorMode.MOVE,
    val word: String = "",
){

}

enum class EditorMode { MOVE, VERTEX,  ARCS}

sealed interface EditorEvent{

}

sealed interface EditorUserEvent : EditorEvent{

    data class ChooseMode(val editorMode: EditorMode) : EditorUserEvent
    data class ChangeWord(val word: String) : EditorUserEvent
}

sealed interface EditorSingleEvent{

}