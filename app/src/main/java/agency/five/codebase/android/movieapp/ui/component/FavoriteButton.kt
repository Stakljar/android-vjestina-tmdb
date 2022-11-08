package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.Blue
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = if (isFavorite) R.drawable.ic_filled_heart else R.drawable.ic_empty_heart),
        contentDescription = null,
        modifier = modifier
            .clickable {
                onClick()
            }
            .size(30.dp)
            .background(Blue.copy(alpha = 0.6F), CircleShape)
            .clip(CircleShape)
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun FavoriteButtonPreview() {
    var isFavorite by remember { mutableStateOf(false) }
    MovieAppTheme {
        FavoriteButton(isFavorite = isFavorite, onClick = { isFavorite = !isFavorite })
    }
}
