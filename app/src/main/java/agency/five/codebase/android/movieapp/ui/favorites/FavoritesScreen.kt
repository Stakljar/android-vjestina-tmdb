package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.Heading
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesRoute(
    viewModel: FavoritesViewModel,
    onNavigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoritesViewState: FavoritesViewState by viewModel.favoritesViewState.collectAsState()

    FavoritesScreen(
        favoritesViewState = favoritesViewState,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onFavoriteButtonClick = viewModel::removeFavorite,
        modifier = modifier
    )
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.movie_card_favorites_width)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.multi_large_spacing)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_spacing)),
        modifier = modifier
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) },
            key = "Favorites Heading"
        ) {
            Heading(
                stringResource(id = R.string.favorites),
                Modifier.padding(
                    start = dimensionResource(id = R.dimen.very_large_spacing),
                    top = dimensionResource(id = R.dimen.large_spacing)
                )
            )
        }
        items(
            items = favoritesViewState.favoritesMovieViewStates,
            key = { favoritesMovieViewState -> favoritesMovieViewState.id }
        ) { favoritesMovieViewState ->
            MovieCard(
                movieCardViewState = favoritesMovieViewState.movieCardViewState,
                onClick = { onNavigateToMovieDetails(favoritesMovieViewState.id) },
                onFavoriteClick = { onFavoriteButtonClick(favoritesMovieViewState.id) },
                Modifier
                    .height(dimensionResource(id = R.dimen.movie_card_favorites_height))
                    .shadow(elevation = 0.dp, shape = RoundedCornerShape(percent = 10))
            )
        }
    }
}

@Preview
@Composable
private fun FavoritesScreenPreview() {
    var _favoritesViewState by remember {
        mutableStateOf(
            FavoritesMapperImpl().toFavoritesViewState(
                MoviesMock.getMoviesList()
            )
        )
    }
    MovieAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            FavoritesScreen(
                favoritesViewState = _favoritesViewState,
                onNavigateToMovieDetails = { },
                onFavoriteButtonClick = { favoritesMovieId ->
                    _favoritesViewState =
                        onFavoriteButtonClick(_favoritesViewState, favoritesMovieId)
                },
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}
