package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MovieLayout(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = movieDetailsViewState.imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = dimensionResource(id = R.dimen.large_spacing))
        ) {
            Row(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_spacing)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserScoreProgressBar(
                    progress = movieDetailsViewState.voteAverage / 10,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.user_score_progress_bar_size))
                )
                Text(
                    text = stringResource(id = R.string.user_score),
                    color = Color.White,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.medium_spacing))
                )
            }
            Text(
                text = movieDetailsViewState.title,
                color = Color.White,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_spacing))
            )
            FavoriteButton(
                isFavorite = movieDetailsViewState.isFavorite,
                onClick = onFavoriteButtonClick,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_spacing))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieLayoutPreview() {
    var movieDetailsViewState by remember {
        mutableStateOf(
            MovieDetailsMapperImpl().toMovieDetailsViewState(
                MoviesMock.getMovieDetails()
            )
        )
    }
    MovieAppTheme {
        MovieLayout(
            movieDetailsViewState = movieDetailsViewState,
            onFavoriteButtonClick = {
                movieDetailsViewState =
                    movieDetailsViewState.copy(isFavorite = !movieDetailsViewState.isFavorite)
            },
            modifier = Modifier.height(480.dp)
        )
    }
}
