package ui.screens

import arch.ViewModel
import kotlinx.coroutines.CoroutineScope

class EditorViewModel(coroutineScope: CoroutineScope) : ViewModel<EditorState, EditorEvent, EditorEvent, EditorSingleEvent>(coroutineScope){


    override val initalState: EditorState = EditorState()

    override fun reduce(state: EditorState, event: EditorEvent): EditorState {
        return when(event){
            is EditorUserEvent.ChooseMode -> {
                state.copy(editorMode = event.editorMode)
            }

            is EditorUserEvent.ChangeWord -> {
                state.copy(word = event.word)
            }
        }
    }

}