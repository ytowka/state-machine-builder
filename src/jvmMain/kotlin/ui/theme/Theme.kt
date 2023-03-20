package ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GlobalTheme(content: @Composable ()->Unit){

    MaterialTheme{
        content()
    }
}
