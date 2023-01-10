package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.Blue
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Heading(text: String, modifier: Modifier = Modifier){
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.h1,
        color = Blue
    )
}

@Preview(showBackground = true)
@Composable
fun HeadingPreview(){
    MovieAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            Heading(text = "What's popular")
        }
    }
}
