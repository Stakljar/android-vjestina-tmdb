package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MoviesWithinCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onNavigateToMovieDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val popularCategoryViewState by viewModel.popularMoviesViewState.collectAsState()
    val nowPlayingCategoryViewState by viewModel.nowPlayingMoviesViewState.collectAsState()
    val upcomingCategoryViewState by viewModel.upcomingMoviesViewState.collectAsState()

    HomeScreen(
        popularCategoryViewState = popularCategoryViewState,
        nowPlayingCategoryViewState = nowPlayingCategoryViewState,
        upcomingCategoryViewState = upcomingCategoryViewState,
        onNavigateToMovieDetails = onNavigateToMovieDetails,
        onCategoryClick = viewModel::changeCategory,
        onFavoriteClick = viewModel::toggleFavorite,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    popularCategoryViewState: HomeMovieCategoryViewState,
    nowPlayingCategoryViewState: HomeMovieCategoryViewState,
    upcomingCategoryViewState: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: (Int) -> Unit,
    onCategoryClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            MoviesWithinCategory(
                headingName = stringResource(id = R.string.whats_popular),
                categoryViewState = popularCategoryViewState,
                onNavigateToMovieDetails = onNavigateToMovieDetails,
                onCategoryClick = onCategoryClick,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.large_spacing),
                    top = dimensionResource(id = R.dimen.large_spacing),
                    bottom = dimensionResource(id = R.dimen.large_spacing)
                )
            )
        }
        item {
            MoviesWithinCategory(
                headingName = stringResource(id = R.string.now_playing),
                categoryViewState = nowPlayingCategoryViewState,
                onNavigateToMovieDetails = onNavigateToMovieDetails,
                onCategoryClick = onCategoryClick,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.large_spacing),
                    top = dimensionResource(id = R.dimen.large_spacing),
                    bottom = dimensionResource(id = R.dimen.large_spacing)
                )
            )
        }
        item {
            MoviesWithinCategory(
                headingName = stringResource(id = R.string.upcoming),
                categoryViewState = upcomingCategoryViewState,
                onNavigateToMovieDetails = onNavigateToMovieDetails,
                onCategoryClick = onCategoryClick,
                onFavoriteClick = onFavoriteClick,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.large_spacing),
                    top = dimensionResource(id = R.dimen.large_spacing),
                    bottom = dimensionResource(id = R.dimen.large_spacing)
                )
            )
        }
    }
}

@Preview
@Composable
private fun MovieDetailsScreenPreview() {
    var _popularCategoryViewState by remember {
        mutableStateOf(
            HomeScreenMapperImpl().toHomeMovieCategoryViewState(
                listOf(
                    MovieCategory.POPULAR_STREAMING,
                    MovieCategory.POPULAR_ON_TV,
                    MovieCategory.POPULAR_FOR_RENT,
                    MovieCategory.POPULAR_IN_THEATRES
                ),
                MovieCategory.POPULAR_STREAMING,
                MoviesMock.getMoviesList()
            )
        )
    }
    var _nowPlayingCategoryViewState by remember {
        mutableStateOf(
            HomeScreenMapperImpl().toHomeMovieCategoryViewState(
                listOf(
                    MovieCategory.NOW_PLAYING_MOVIES,
                    MovieCategory.NOW_PLAYING_TV,
                ),
                MovieCategory.NOW_PLAYING_MOVIES,
                MoviesMock.getMoviesList()
            )
        )
    }
    var _upcomingCategoryViewState by remember {
        mutableStateOf(
            HomeScreenMapperImpl().toHomeMovieCategoryViewState(
                listOf(
                    MovieCategory.UPCOMING_TODAY,
                    MovieCategory.UPCOMING_THIS_WEEK,
                ),
                MovieCategory.UPCOMING_TODAY,
                MoviesMock.getMoviesList()
            )
        )
    }

    MovieAppTheme {
        HomeScreen(
            popularCategoryViewState = _popularCategoryViewState,
            nowPlayingCategoryViewState = _nowPlayingCategoryViewState,
            upcomingCategoryViewState = _upcomingCategoryViewState,
            onNavigateToMovieDetails = { },
            onCategoryClick = { categoryId ->
                when (categoryId) {

                    in 0..3 -> {
                        _popularCategoryViewState =
                            changeCategory(_popularCategoryViewState, categoryId)
                    }
                    in 4..5 -> {
                        _nowPlayingCategoryViewState =
                            changeCategory(_nowPlayingCategoryViewState, categoryId)
                    }
                    else -> {
                        _upcomingCategoryViewState =
                            changeCategory(_upcomingCategoryViewState, categoryId)
                    }
                }
            },
            onFavoriteClick = { movieId ->
                if(_popularCategoryViewState.movies.any { it.id == movieId })
                    _popularCategoryViewState =
                        changeMovieFavoriteStatus(_popularCategoryViewState, movieId)
                if(_nowPlayingCategoryViewState.movies.any { it.id == movieId })
                    _nowPlayingCategoryViewState =
                        changeMovieFavoriteStatus(_nowPlayingCategoryViewState, movieId)
                if(_upcomingCategoryViewState.movies.any { it.id == movieId })
                    _upcomingCategoryViewState =
                        changeMovieFavoriteStatus(_upcomingCategoryViewState, movieId)
            }
        )
    }
}
