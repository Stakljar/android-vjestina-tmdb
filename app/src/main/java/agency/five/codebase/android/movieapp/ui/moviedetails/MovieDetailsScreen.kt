package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.CastLayout
import agency.five.codebase.android.movieapp.ui.component.CrewLayout
import agency.five.codebase.android.movieapp.ui.component.Heading
import agency.five.codebase.android.movieapp.ui.component.MovieLayout
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MovieDetailsRoute(
    viewModel: MovieDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val movieDetailsViewState: MovieDetailsViewState by viewModel.movieDetailsViewState.collectAsState()

    MovieDetailsScreen(
        movieDetailsViewState = movieDetailsViewState,
        onFavoriteButtonClick = {
            viewModel.toggleFavorite(movieDetailsViewState.id)
        },
        modifier = modifier
    )
}

@Composable
fun MovieDetailsScreen(
    movieDetailsViewState: MovieDetailsViewState,
    onFavoriteButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        MovieLayout(
            movieDetailsViewState = movieDetailsViewState,
            onFavoriteButtonClick = onFavoriteButtonClick,
            modifier = Modifier.height(dimensionResource(id = R.dimen.movie_details_image_size))
        )
        Heading(
            text = stringResource(id = R.string.overview),
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.large_spacing),
                top = dimensionResource(id = R.dimen.large_spacing),
                end = dimensionResource(id = R.dimen.large_spacing),
                bottom = dimensionResource(id = R.dimen.small_spacing)
            )
        )
        Text(
            text = movieDetailsViewState.overview,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.large_spacing),
                end = dimensionResource(id = R.dimen.large_spacing),
                bottom = dimensionResource(id = R.dimen.large_spacing)
            )
        )
        CrewLayout(
            crew = movieDetailsViewState.crew, modifier = Modifier
                .height(dimensionResource(id = R.dimen.crew_layout_size))
                .padding(
                    start = dimensionResource(id = R.dimen.large_spacing),
                    end = dimensionResource(id = R.dimen.large_spacing),
                    bottom = dimensionResource(id = R.dimen.very_large_spacing)
                )
        )
        Heading(
            text = stringResource(id = R.string.top_billed_cast),
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.large_spacing),
                bottom = dimensionResource(id = R.dimen.medium_spacing),
                end = dimensionResource(id = R.dimen.large_spacing)
            )
        )
        CastLayout(
            cast = movieDetailsViewState.cast,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.large_spacing),
                bottom = dimensionResource(id = R.dimen.large_spacing)
            )
        )
    }
}

@Preview
@Composable
private fun MovieDetailsScreenPreview() {
    var _movieDetailsViewState by remember {
        mutableStateOf(
            MovieDetailsMapperImpl().toMovieDetailsViewState(
                MoviesMock.getMovieDetails()
            )
        )
    }
    MovieAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            MovieDetailsScreen(
                movieDetailsViewState = _movieDetailsViewState,
                onFavoriteButtonClick = {
                    _movieDetailsViewState =
                        _movieDetailsViewState.copy(isFavorite = !_movieDetailsViewState.isFavorite)
                },
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}
