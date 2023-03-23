package ui.screens

import arch.ViewModel
import kotlinx.coroutines.CoroutineScope
import ui.models.graph.Arc
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
                        vertices = graph.vertices + listOf(VertexView("name", event.offset, graph.vertices.size))
                    )
                )
            }

            is EditorUserEvent.MoveVertex -> with(state){
                copy(
                    graph = graph.copy(
                        vertices = graph.vertices.mapIndexed { index, vertex ->
                            if(index == event.index){
                                vertex.copy(pos = event.offset)
                            }else vertex
                        },
                    )
                )
            }
            is EditorUserEvent.MoveLinkage -> with(state){
                if(currentLinkage != null){
                    copy(currentLinkage = currentLinkage.copy(to = event.offset))
                }else this
            }

            is EditorUserEvent.CaptureVertex -> with(state){
                when(editorMode){
                    EditorMode.MOVE -> this
                    EditorMode.VERTEX -> copy(
                        capturedVertexIndex = event.index
                    )
                    EditorMode.ARCS -> copy(
                        currentLinkage = Linkage(graph.vertices[event.index], graph.vertices[event.index].pos)
                    )
                }
            }
            is EditorUserEvent.ReleaseVertex -> with(state){
                when(editorMode){
                    EditorMode.MOVE -> this
                    EditorMode.VERTEX -> copy(
                        capturedVertexIndex = null
                    )
                    EditorMode.ARCS -> {
                        if(currentLinkage != null){
                            event.atIndex?.takeIf { graph.vertices[it] != currentLinkage.from }?.let {
                                copy(
                                    currentLinkage = null,
                                    graph = graph.copy(
                                        arcs = graph.arcs + Arc("", currentLinkage.from, graph.vertices[event.atIndex])
                                    )
                                )
                            }?: copy(currentLinkage = null)
                        }else this

                    }
                }
            }
        }
    }

}