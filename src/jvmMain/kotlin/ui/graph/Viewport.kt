package ui.graph

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

class Viewport(
    windowSize: Size,
    windowOffset: Offset = Offset(windowSize.width/2, windowSize.height/2),
    size: Size,
    center: Offset = Offset(0f,0f)
) {

    var windowSize: Size by mutableStateOf(windowSize, structuralEqualityPolicy())
    var windowOffset: Offset by mutableStateOf(windowOffset, structuralEqualityPolicy())
    var size: Size by mutableStateOf(size, structuralEqualityPolicy())
    var center: Offset by mutableStateOf(center, structuralEqualityPolicy())

    var scale: Float
        get() = size.width / windowSize.width
        set(value) {
            size *= value
        }

    fun zoom(scale: Float, anchor: Offset){
        val viewportAnchor = anchor.toViewportOffset()
        size *= scale
        val newViewportAnchor = anchor.toViewportOffset()
        center += (viewportAnchor - newViewportAnchor)
    }

    fun translate(delta: Offset){
        center += delta * scale
    }

    fun Offset.toViewportOffset(): Offset{
        return Offset(
            x = (x - windowOffset.x) * scale + center.x,
            y = (y - windowOffset.y) * scale + center.y
        )
    }

    fun Offset.toWindowOffset(): Offset{
        return Offset(
            x = (x - center.x) / scale + windowOffset.x,
            y = (y - center.y) / scale + windowOffset.y,
        )
    }

    fun Size.toViewportSize(): Size{
        return Size(
            width = width * scale,
            height = height * scale,
        )
    }

    fun Size.toWindowSize(): Size{
        return Size(
            width = width / scale,
            height = height / scale,
        )
    }
}

@Composable
fun rememberViewport(
    windowSize: Size
): Viewport{
    val viewport = remember {
        Viewport(
            windowSize = windowSize,
            size = windowSize,
        )
    }
    LaunchedEffect(windowSize){
        val viewportWidth = windowSize.width * viewport.scale
        val viewportHeight = windowSize.height * viewport.scale

        viewport.windowOffset = Offset(windowSize.width/2, windowSize.height/2)
        println(viewport.windowOffset)
        viewport.windowSize = windowSize
        viewport.size = Size(viewportWidth, viewportHeight)
    }
    return viewport
}

private operator fun Size.plus(size: Size): Size {
    return Size(width + size.width, height + size.height)
}

