package ui.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerKeyboardModifiers
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Graph(
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints {
        val windowSize = with(LocalDensity.current) {
            Size(maxWidth.toPx(), maxHeight.toPx())
        }
        val viewport = rememberViewport(windowSize)

        Canvas(
            modifier = modifier
                .onPointerEvent(
                    PointerEventType.Scroll,
                ) {
                    val zoomFactor = 0.1f
                    PointerKeyboardModifiers(1)
                    viewport.zoom(1 + it.changes[0].scrollDelta.y * zoomFactor, (it.changes[0].position))
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        viewport.translate(-dragAmount)
                    }
                },
        ) {
           /* drawRect(
                color = Color.Blue,
                topLeft = center,
                size = Size(this.size.width / 2 - 50, this.size.height / 2 - 50)
            )*/
            with(viewport){
                drawCircle(
                    color = Color.Red,
                    radius = 50f / scale,
                    center = Offset.Zero.toWindowOffset(),
                )

                drawCircle(
                    color = Color.Blue,
                    radius = 20f / scale,
                    center = Offset(300f, 100f).toWindowOffset(),
                    )
            }
        }
    }
}