package ui.models.graph

import androidx.compose.ui.geometry.Offset

data class VertexView(
    val name: String,
    val pos: Offset
){
    fun checkIsIn(offset: Offset) : Boolean{
        return (
                pos.x - DEFAULT_SIZE / 2 < offset.x &&
                pos.x + DEFAULT_SIZE / 2 > offset.x &&
                pos.y - DEFAULT_SIZE / 2 < offset.y &&
                pos.y + DEFAULT_SIZE / 2 > offset.y
                )
    }
    companion object{
        const val DEFAULT_SIZE = 25f;
    }
}
