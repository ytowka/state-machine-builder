package ui.models.graph

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset

@Immutable
data class GraphView(
    val vertices: List<VertexView> = listOf(),
    val arcs: List<Arc> = listOf(),
){
    fun getCapturedVertex(offset: Offset): Int?{
        vertices.forEachIndexed { index, vertex ->
            if (vertex.checkIsIn(offset)) {
                return index
            }
        }
        return null
    }
}