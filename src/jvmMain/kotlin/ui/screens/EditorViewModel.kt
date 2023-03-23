package ui.screens

import arch.ViewModel
import kotlinx.coroutines.CoroutineScope
import ui.models.graph.VertexView

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

            is EditorUserEvent.AddVertex -> with(state){
                copy(
                    graph = graph.copy(
                        vertices = graph.vertices + listOf(VertexView("name", event.offset))
                    )
                )
            }

            is EditorUserEvent.MoveVertex -> with(state){
                copy(
                    graph = graph.copy(
                        vertices = graph.vertices.mapIndexed { index, vertex ->
                            if(index == event.index){
                                vertex.copy(
                                    pos = event.offset
                                )
                            }else vertex
                        }
                    )
                )
            }

            is EditorUserEvent.CaptureVertex -> {
                state.copy(
                    capturedVertexIndex = event.index
                )
            }
            is EditorUserEvent.ReleaseVertex -> {
                state.copy(
                    capturedVertexIndex = null
                )
            }
        }
    }

}