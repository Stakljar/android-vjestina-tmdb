package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class MovieCardViewState(
    val imageUrl: String?,
    val isFavorite: Boolean
)

@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Box {
            AsyncImage(
                model = movieCardViewState.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .clickable { onClick() }
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            FavoriteButton(
                isFavorite = movieCardViewState.isFavorite,
                onClick = { onFavoriteClick() },
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp)
                    .align(Alignment.TopStart)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieCardPreview() {
    var movieCardViewState by remember {
        mutableStateOf(
            MovieCardViewState(
                imageUrl = "https://image.tmdb.org/t/p/w500/78lPtwv72eTNqFW9COBYI0dWDJa.jpg",
                isFavorite = false
            )
        )
    }
    MovieAppTheme {
        MovieCard(
            movieCardViewState = movieCardViewState,
            onClick = { },
            onFavoriteClick = {
                movieCardViewState =
                    movieCardViewState.copy(isFavorite = !movieCardViewState.isFavorite)
            },
            Modifier
                .size(130.dp, 200.dp)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(10.dp))
        )
    }
}
