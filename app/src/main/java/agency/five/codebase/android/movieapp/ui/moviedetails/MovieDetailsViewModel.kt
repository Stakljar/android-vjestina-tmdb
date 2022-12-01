package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    movieDetailsMapper: MovieDetailsMapper,
    movieId: Int
) : ViewModel() {
    private val _movieDetailsViewState = MutableStateFlow(movieDetailsMapper.toMovieDetailsViewState(MoviesMock.getMovieDetails(movieId)))
    val movieDetailsViewState: StateFlow<MovieDetailsViewState> = movieRepository.movieDetails(movieId)
        .map { movies -> movieDetailsMapper.toMovieDetailsViewState(movies) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, _movieDetailsViewState.value)

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
