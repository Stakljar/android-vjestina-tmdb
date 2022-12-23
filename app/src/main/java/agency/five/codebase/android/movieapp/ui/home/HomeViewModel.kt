package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    homeScreenMapper: HomeScreenMapper,
) : ViewModel() {
    private val _selectedPopularCategoryId: MutableStateFlow<MovieCategory> = MutableStateFlow(MovieCategory.POPULAR_STREAMING)

    val popularMoviesViewState: StateFlow<HomeMovieCategoryViewState> = _selectedPopularCategoryId.flatMapLatest {
        movieRepository.movies(_selectedPopularCategoryId.value)
            .map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    listOf(
                        MovieCategory.POPULAR_STREAMING,
                        MovieCategory.POPULAR_ON_TV,
                        MovieCategory.POPULAR_FOR_RENT,
                        MovieCategory.POPULAR_IN_THEATRES
                    ),
                    _selectedPopularCategoryId.value, movies
                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), HomeMovieCategoryViewState(emptyList(), emptyList()))

    private val _selectedNowPlayingCategoryId: MutableStateFlow<MovieCategory> = MutableStateFlow(MovieCategory.NOW_PLAYING_MOVIES)

    val nowPlayingMoviesViewState: StateFlow<HomeMovieCategoryViewState> = _selectedNowPlayingCategoryId.flatMapLatest {
        movieRepository.movies(_selectedNowPlayingCategoryId.value)
            .map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    listOf(
                        MovieCategory.NOW_PLAYING_MOVIES,
                        MovieCategory.NOW_PLAYING_TV,
                    ),
                    _selectedNowPlayingCategoryId.value, movies
                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), HomeMovieCategoryViewState(emptyList(), emptyList()))

    private val _selectedUpcomingCategoryId: MutableStateFlow<MovieCategory> = MutableStateFlow(MovieCategory.UPCOMING_TODAY)

    val upcomingMoviesViewState: StateFlow<HomeMovieCategoryViewState> = _selectedUpcomingCategoryId.flatMapLatest{
        movieRepository.movies(_selectedUpcomingCategoryId.value)
            .map { movies ->
                homeScreenMapper.toHomeMovieCategoryViewState(
                    listOf(
                        MovieCategory.UPCOMING_TODAY,
                        MovieCategory.UPCOMING_THIS_WEEK,
                    ),
                    _selectedUpcomingCategoryId.value, movies
                )
            }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), HomeMovieCategoryViewState(emptyList(), emptyList()))

    fun changeCategory(categoryId: Int) {
        when (categoryId) {

            MovieCategory.POPULAR_STREAMING.ordinal,
            MovieCategory.POPULAR_ON_TV.ordinal,
            MovieCategory.POPULAR_FOR_RENT.ordinal,
            MovieCategory.POPULAR_IN_THEATRES.ordinal-> {
                _selectedPopularCategoryId.value = MovieCategory.values()[categoryId]
            }
            MovieCategory.NOW_PLAYING_MOVIES.ordinal,
            MovieCategory.NOW_PLAYING_TV.ordinal -> {
                _selectedNowPlayingCategoryId.value = MovieCategory.values()[categoryId]
            }
            else -> {
                _selectedUpcomingCategoryId.value = MovieCategory.values()[categoryId]
            }
        }
    }

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
