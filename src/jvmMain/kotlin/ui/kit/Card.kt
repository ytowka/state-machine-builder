package ui.kit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun Card(
    modifier: Modifier = Modifier,
    content: @Composable ()->Unit,
){
    Box(
        modifier = modifier
            .shadow(elevation = 2.dp, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.background, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ){
        content()
    }
}