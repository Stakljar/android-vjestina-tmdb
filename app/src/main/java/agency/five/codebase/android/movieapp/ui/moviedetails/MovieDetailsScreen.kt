package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.CastLayout
import agency.five.codebase.android.movieapp.ui.component.CrewLayout
import agency.five.codebase.android.movieapp.ui.component.Heading
import agency.five.codebase.android.movieapp.ui.component.MovieLayout
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val movieDetailsMapper: MovieDetailsMapper = MovieDetailsMapperImpl()

val movieDetailsViewState = movieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails())

@Composable
fun MovieDetailsRoute(
) {
    var movieDetailsViewState by remember { mutableStateOf(movieDetailsViewState) }
    MovieDetailsScreen(
        movieDetailsViewState,
        {
            movieDetailsViewState =
                movieDetailsViewState.copy(isFavorite = !movieDetailsViewState.isFavorite)
        },
    )
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            MovieLayout(
                movieDetailsViewState = movieDetailsViewState,
                onFavoriteButtonClick = onFavoriteButtonClick,
                modifier = Modifier.height(dimensionResource(id = R.dimen.movie_details_image_size))
            )
        }
        item {
            Heading(
                text = stringResource(id = R.string.overview),
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.large_spacing),
                    top = dimensionResource(id = R.dimen.large_spacing),
                    end = dimensionResource(id = R.dimen.large_spacing),
                    bottom = dimensionResource(id = R.dimen.small_spacing)
                )
            )
        }
        item {
            Text(
                text = movieDetailsViewState.overview,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.large_spacing),
                    end = dimensionResource(id = R.dimen.large_spacing),
                    bottom = dimensionResource(id = R.dimen.large_spacing)
                )
            )
        }
        item {
            CrewLayout(
                crew = movieDetailsViewState.crew, modifier = Modifier
                    .height(dimensionResource(id = R.dimen.crew_layout_size))
                    .padding(
                        start = dimensionResource(id = R.dimen.large_spacing),
                        end = dimensionResource(id = R.dimen.large_spacing),
                        bottom = dimensionResource(id = R.dimen.very_large_spacing)
                    )
            )
        }
        item {
            Heading(
                text = stringResource(id = R.string.top_billed_cast),
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.large_spacing),
                    bottom = dimensionResource(id = R.dimen.medium_spacing),
                    end = dimensionResource(id = R.dimen.large_spacing)
                )
            )
        }
        item {
            CastLayout(
                cast = movieDetailsViewState.cast,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.large_spacing),
                    bottom = dimensionResource(id = R.dimen.large_spacing)
                )
            )
        }
    }
}

@Preview
@Composable
private fun MovieDetailsScreenPreview() {
    var movieDetailsViewState by remember { mutableStateOf(movieDetailsViewState) }
    MovieAppTheme {
        MovieDetailsScreen(
            movieDetailsViewState,
            {
                movieDetailsViewState =
                    movieDetailsViewState.copy(isFavorite = !movieDetailsViewState.isFavorite)
            },
            Modifier.padding(10.dp)
        )
    }
}
