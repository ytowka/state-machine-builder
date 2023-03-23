package ui.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.*
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
import ui.models.graph.GraphView
import ui.models.graph.VertexView
import ui.screens.Linkage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Graph(
    modifier: Modifier = Modifier,
    graphView: GraphView,
    captureVertexIndex: ()->Int?,
    linkage: ()->Linkage?,
    onClick: (Offset) -> Unit,
    onCaptureVertex: (Int) -> Unit,
    onReleaseVertex: (Int?) -> Unit,
    onVertexMove: (offset: Offset, index: Int) -> Unit,
    onLinkageMove: (Offset) -> Unit,
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
                .onPointerEvent(PointerEventType.Press){
                    with(viewport) {
                        val viewPortPoint = it.changes[0].position.toViewportOffset()
                        graphView.getCapturedVertex(viewPortPoint)?.let {
                            onCaptureVertex(it)
                        }
                    }
                }
                .onPointerEvent(PointerEventType.Release){
                    with(viewport) {
                        val viewPortPoint = it.changes[0].position.toViewportOffset()
                        onReleaseVertex(graphView.getCapturedVertex(viewPortPoint))
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        if(linkage() != null){
                            with(viewport){ onLinkageMove(change.position.toViewportOffset()) }
                        } else if(captureVertexIndex() != null ){
                            with(viewport){ onVertexMove(change.position.toViewportOffset(), captureVertexIndex()!!) }
                        }else{
                            viewport.translate(-dragAmount)
                        }
                    }
                }
                .pointerInput(Unit){
                    detectTapGestures {
                        with(viewport){ onClick(it.toViewportOffset()) }
                    }
                }
            ,
        ) {
            with(viewport){
                graphView.arcs.forEach {
                    drawLine(
                        color = Color.Red,
                        start = it.from.pos.toWindowOffset(),
                        end = it.to.pos.toWindowOffset(),
                        strokeWidth = 5 / scale
                    )
                }
                linkage()?.let {
                    drawLine(
                        color = Color.Red,
                        start = it.from.pos.toWindowOffset(),
                        end = it.to.toWindowOffset(),
                        strokeWidth = 5 / scale
                    )
                }
                graphView.vertices.forEach{
                    drawCircle(
                        color = Color.Black,
                        radius = VertexView.DEFAULT_SIZE / scale,
                        center = it.pos.toWindowOffset(),
                    )
                }
            }

        }
    }
}