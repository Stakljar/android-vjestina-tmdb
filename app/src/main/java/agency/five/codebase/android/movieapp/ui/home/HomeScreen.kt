package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()

val popularCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    listOf(
        MovieCategory.POPULAR_STREAMING,
        MovieCategory.POPULAR_ONTV,
        MovieCategory.POPULAR_FORRENT,
        MovieCategory.POPULAR_INTHREATRES
    ),
    MovieCategory.POPULAR_STREAMING,
    MoviesMock.getMoviesList()
)
val nowPlayingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    listOf(
        MovieCategory.NOWPLAYING_MOVIES,
        MovieCategory.NOWPLAYING_TV,
    ),
    MovieCategory.NOWPLAYING_MOVIES,
    MoviesMock.getMoviesList()
)
val upcomingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    listOf(
        MovieCategory.UPCOMING_TODAY,
        MovieCategory.UPCOMING_THISWEEK,
    ),
    MovieCategory.UPCOMING_TODAY,
    MoviesMock.getMoviesList()
)

@Composable
fun HomeRoute(
    onNavigateToMovieDetails: (Int) -> Unit
) {
    var popularCategoryViewState by remember { mutableStateOf(popularCategoryViewState) }
    var nowPlayingCategoryViewState by remember { mutableStateOf(nowPlayingCategoryViewState) }
    var upcomingCategoryViewState by remember { mutableStateOf(upcomingCategoryViewState) }
    HomeScreen(
        popularCategoryViewState,
        nowPlayingCategoryViewState,
        upcomingCategoryViewState,
        onNavigateToMovieDetails,
        onCategoryClick = { category ->
            when (category.itemId) {

                in 0..3 -> {
                    popularCategoryViewState =
                        changeCategory(popularCategoryViewState, category.itemId)
                }
                in 4..5 -> {
                    nowPlayingCategoryViewState =
                        changeCategory(nowPlayingCategoryViewState, category.itemId)
                }
                else -> {
                    upcomingCategoryViewState =
                        changeCategory(upcomingCategoryViewState, category.itemId)
                }
            }
        },
        onFavoriteClick = { category, movie ->
            when (category.itemId) {

                in 0..3 -> {
                    popularCategoryViewState =
                        changeMovieFavoriteStatus(popularCategoryViewState, movie)
                }
                in 4..5 -> {
                    nowPlayingCategoryViewState =
                        changeMovieFavoriteStatus(nowPlayingCategoryViewState, movie)
                }
                else -> {
                    upcomingCategoryViewState =
                        changeMovieFavoriteStatus(upcomingCategoryViewState, movie)
                }
            }
        }
    )
}

@Composable
fun HomeScreen(
    popularCategoryViewState: HomeMovieCategoryViewState,
    nowPlayingCategoryViewState: HomeMovieCategoryViewState,
    upcomingCategoryViewState: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: (Int) -> Unit,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onFavoriteClick: (MovieCategoryLabelViewState, HomeMovieViewState) -> Unit,
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
    var popularCategoryViewState by remember { mutableStateOf(popularCategoryViewState) }
    var nowPlayingCategoryViewState by remember { mutableStateOf(nowPlayingCategoryViewState) }
    var upcomingCategoryViewState by remember { mutableStateOf(upcomingCategoryViewState) }
    MovieAppTheme {
        HomeScreen(
            popularCategoryViewState,
            nowPlayingCategoryViewState,
            upcomingCategoryViewState,
            { },
            onCategoryClick = { category ->
                when (category.itemId) {

                    in 0..3 -> {
                        popularCategoryViewState =
                            changeCategory(popularCategoryViewState, category.itemId)
                    }
                    in 4..5 -> {
                        nowPlayingCategoryViewState =
                            changeCategory(nowPlayingCategoryViewState, category.itemId)
                    }
                    else -> {
                        upcomingCategoryViewState =
                            changeCategory(upcomingCategoryViewState, category.itemId)
                    }
                }
            },
            onFavoriteClick = { category, movie ->
                when (category.itemId) {

                    in 0..3 -> {
                        popularCategoryViewState =
                            changeMovieFavoriteStatus(popularCategoryViewState, movie)
                    }
                    in 4..5 -> {
                        nowPlayingCategoryViewState =
                            changeMovieFavoriteStatus(nowPlayingCategoryViewState, movie)
                    }
                    else -> {
                        upcomingCategoryViewState =
                            changeMovieFavoriteStatus(upcomingCategoryViewState, movie)
                    }
                }
            }
        )
    }
}
