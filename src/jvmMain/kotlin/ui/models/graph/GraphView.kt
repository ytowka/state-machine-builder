package ui.models.graph

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset

@Immutable
data class GraphView(
    val vertices: List<VertexView> = listOf(),
    //val arcs: List<Arc> = listOf(),
){
    val arcs: List<Arc> by lazy {
        val arcs = mutableListOf<Arc>()

        for(i in 0..vertices.lastIndex){
            for (j in i+1..vertices.lastIndex){
                arcs.add(Arc("", vertices[i], vertices[j]))
            }
        }

        arcs
    }

    fun getCapturedVertex(offset: Offset): Int?{
        vertices.forEachIndexed { index, vertex ->
            if (vertex.checkIsIn(offset)) {
                return index
            }
        }
        return null
    }
}