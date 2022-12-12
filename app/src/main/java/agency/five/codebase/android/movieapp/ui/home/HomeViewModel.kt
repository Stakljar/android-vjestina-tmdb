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
    private val _selectedPopularCategoryId: MutableStateFlow<Int> = MutableStateFlow(MovieCategory.POPULAR_STREAMING.ordinal)

    val popularMoviesViewState: StateFlow<HomeMovieCategoryViewState> = _selectedPopularCategoryId.flatMapLatest {
        movieRepository.popularMovies(MovieCategory.values()[it])
            .map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    listOf(
                        MovieCategory.POPULAR_STREAMING,
                        MovieCategory.POPULAR_ON_TV,
                        MovieCategory.POPULAR_FOR_RENT,
                        MovieCategory.POPULAR_IN_THEATRES
                    ),
                    MovieCategory.values()[it], movies
                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), HomeMovieCategoryViewState(emptyList(), emptyList()))

    private val _selectedNowPlayingPopularCategoryId: MutableStateFlow<Int> = MutableStateFlow(MovieCategory.NOW_PLAYING_MOVIES.ordinal)

    val nowPlayingMoviesViewState: StateFlow<HomeMovieCategoryViewState> = _selectedNowPlayingPopularCategoryId.flatMapLatest {
        movieRepository.popularMovies(MovieCategory.values()[it])
            .map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    listOf(
                        MovieCategory.NOW_PLAYING_MOVIES,
                        MovieCategory.NOW_PLAYING_TV,
                    ),
                    MovieCategory.values()[it], movies
                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), HomeMovieCategoryViewState(emptyList(), emptyList()))

    private val _selectedUpcomingPopularCategoryId: MutableStateFlow<Int> = MutableStateFlow(MovieCategory.UPCOMING_TODAY.ordinal)

    val upcomingMoviesViewState: StateFlow<HomeMovieCategoryViewState> = _selectedUpcomingPopularCategoryId.flatMapLatest{
        movieRepository.popularMovies(MovieCategory.values()[it])
            .map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    listOf(
                        MovieCategory.UPCOMING_TODAY,
                        MovieCategory.UPCOMING_THIS_WEEK,
                    ),
                    MovieCategory.values()[it], movies
                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), HomeMovieCategoryViewState(emptyList(), emptyList()))

    fun changeCategory(categoryId: Int) {
        when (categoryId) {

            MovieCategory.POPULAR_STREAMING.ordinal,
            MovieCategory.POPULAR_ON_TV.ordinal,
            MovieCategory.POPULAR_FOR_RENT.ordinal,
            MovieCategory.POPULAR_IN_THEATRES.ordinal-> {
                _selectedPopularCategoryId.value = categoryId
            }
            MovieCategory.NOW_PLAYING_MOVIES.ordinal,
            MovieCategory.NOW_PLAYING_TV.ordinal -> {
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
