package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    homeScreenMapper: HomeScreenMapper,
) : ViewModel() {
    private val initialPopularMoviesViewState =
        movieRepository.popularMovies(MovieCategory.POPULAR_STREAMING)
            .map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    listOf(
                        MovieCategory.POPULAR_STREAMING,
                        MovieCategory.POPULAR_ONTV,
                        MovieCategory.POPULAR_FORRENT,
                        MovieCategory.POPULAR_INTHREATRES
                    ),
                    MovieCategory.POPULAR_STREAMING, movies
                )
            }

    private val _selectedPopularCategoryId: MutableStateFlow<Int> = MutableStateFlow(0)

    private val _popularMoviesViewState: MutableStateFlow<HomeMovieCategoryViewState> =
        MutableStateFlow(HomeMovieCategoryViewState(emptyList(), emptyList()))

    val popularMoviesViewState: StateFlow<HomeMovieCategoryViewState> = combine(
        initialPopularMoviesViewState,
        _selectedPopularCategoryId
    ) { initialPopularMoviesViewState, selectedCategoryId ->
        val movieCategories = initialPopularMoviesViewState.movieCategories.map {
            it.copy(isSelected = selectedCategoryId == it.itemId)
        }
        initialPopularMoviesViewState.copy(movieCategories = movieCategories)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, _popularMoviesViewState.value)

    private val initialNowPlayingMoviesViewState =
        movieRepository.popularMovies(MovieCategory.NOWPLAYING_MOVIES)
            .map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    listOf(
                        MovieCategory.NOWPLAYING_MOVIES,
                        MovieCategory.NOWPLAYING_TV,
                    ),
                    MovieCategory.NOWPLAYING_MOVIES, movies
                )
            }

    private val _selectedNowPlayingPopularCategoryId: MutableStateFlow<Int> = MutableStateFlow(4)

    private val _nowPlayingMoviesViewState: MutableStateFlow<HomeMovieCategoryViewState> =
        MutableStateFlow(HomeMovieCategoryViewState(emptyList(), emptyList()))

    val nowPlayingMoviesViewState: StateFlow<HomeMovieCategoryViewState> = combine(
        initialNowPlayingMoviesViewState,
        _selectedNowPlayingPopularCategoryId
    ) { initialNowPlayingMoviesViewState, selectedCategoryId ->
        val movieCategories = initialNowPlayingMoviesViewState.movieCategories.map {
            it.copy(isSelected = selectedCategoryId == it.itemId)
        }
        initialNowPlayingMoviesViewState.copy(movieCategories = movieCategories)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, _nowPlayingMoviesViewState.value)

    private val initialUpcomingMoviesViewState =
        movieRepository.popularMovies(MovieCategory.UPCOMING_TODAY)
            .map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    listOf(
                        MovieCategory.UPCOMING_TODAY,
                        MovieCategory.UPCOMING_THISWEEK,
                    ),
                    MovieCategory.UPCOMING_TODAY, movies
                )
            }

    private val _selectedUpcomingPopularCategoryId: MutableStateFlow<Int> = MutableStateFlow(6)

    private val _upcomingMoviesViewState: MutableStateFlow<HomeMovieCategoryViewState> =
        MutableStateFlow(HomeMovieCategoryViewState(emptyList(), emptyList()))

    val upcomingMoviesViewState: StateFlow<HomeMovieCategoryViewState> = combine(
        initialUpcomingMoviesViewState,
        _selectedUpcomingPopularCategoryId
    ) { initialUpcomingMoviesViewState, selectedCategoryId ->
        val movieCategories = initialUpcomingMoviesViewState.movieCategories.map {
            it.copy(isSelected = selectedCategoryId == it.itemId)
        }
        initialUpcomingMoviesViewState.copy(movieCategories = movieCategories)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, _upcomingMoviesViewState.value)

    fun changeCategory(categoryId: Int) {
        when (categoryId) {

            in 0..3 -> {
                _selectedPopularCategoryId.value = categoryId
            }
            in 4..5 -> {
                _selectedNowPlayingPopularCategoryId.value = categoryId
            }
            else -> {
                _selectedUpcomingPopularCategoryId.value = categoryId
            }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
