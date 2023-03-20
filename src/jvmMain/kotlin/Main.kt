import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.screens.Editor
import ui.screens.EditorViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val coroutineScope = rememberCoroutineScope()
        val editorViewModel = remember { EditorViewModel(coroutineScope) }


        Editor(editorViewModel)
    }
}

fun main(){
    application {
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
    }
}
