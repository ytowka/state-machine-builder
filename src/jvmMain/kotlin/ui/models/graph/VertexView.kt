package ui.models.graph

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset

data class VertexView(
    val name: String,
    val pos: MutableState<Offset>,
    val index: Int,
){
    fun checkIsIn(offset: Offset) : Boolean{
        return (
                pos.value.x - DEFAULT_SIZE / 2 < offset.x &&
                pos.value.x + DEFAULT_SIZE / 2 > offset.x &&
                pos.value.y - DEFAULT_SIZE / 2 < offset.y &&
                pos.value.y + DEFAULT_SIZE / 2 > offset.y
                )
    }
    companion object{
        const val DEFAULT_SIZE = 25f;
    }
}
