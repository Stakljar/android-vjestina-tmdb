package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    movieDetailsMapper: MovieDetailsMapper,
    movieId: Int
) : ViewModel() {
    val movieDetailsViewState: StateFlow<MovieDetailsViewState> =
        movieRepository.movieDetails(movieId)
            .map { movies -> movieDetailsMapper.toMovieDetailsViewState(movies) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(1000L),
                MovieDetailsViewState(
                    id = 0,
                    imageUrl = null,
                    voteAverage = 9.5F,
                    title = "",
                    overview = "",
                    isFavorite = false,
                    crew = listOf(),
                    cast = listOf()
                )
            )

    fun toggleFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.toggleFavorite(movieId)
        }
    }
}
