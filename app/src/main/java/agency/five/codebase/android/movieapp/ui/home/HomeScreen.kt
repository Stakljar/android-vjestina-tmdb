package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.data.repository.FakeMovieRepository
import agency.five.codebase.android.movieapp.ui.component.MoviesWithinCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.coroutines.Dispatchers

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

class HomeViewModelProvider : PreviewParameterProvider<HomeViewModel> {
    override val values: Sequence<HomeViewModel>
        get() = sequenceOf(
            HomeViewModel(
                movieRepository = FakeMovieRepository(Dispatchers.Default),
                homeScreenMapper = HomeScreenMapperImpl()
            )
        )
}

@Preview
@Composable
private fun MovieDetailsScreenPreview(
    @PreviewParameter(HomeViewModelProvider::class) viewModel: HomeViewModel
) {
    val popularCategoryViewState by viewModel.popularMoviesViewState.collectAsState()
    val nowPlayingCategoryViewState by viewModel.nowPlayingMoviesViewState.collectAsState()
    val upcomingCategoryViewState by viewModel.upcomingMoviesViewState.collectAsState()

    MovieAppTheme {
        HomeScreen(
            popularCategoryViewState = popularCategoryViewState,
            nowPlayingCategoryViewState = nowPlayingCategoryViewState,
            upcomingCategoryViewState = upcomingCategoryViewState,
            onNavigateToMovieDetails = { },
            onCategoryClick = viewModel::changeCategory,
            onFavoriteClick = viewModel::toggleFavorite
        )
    }
}
