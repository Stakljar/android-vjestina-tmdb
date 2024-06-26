package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.data.repository.MovieRepository
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieRepository: MovieRepository,
    favoritesMapper: FavoritesMapper,
) : ViewModel() {
    val favoritesViewState: StateFlow<FavoritesViewState> = movieRepository.favoriteMovies()
        .map { movies -> favoritesMapper.toFavoritesViewState(movies) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), FavoritesViewState(emptyList()))

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.removeMovieFromFavorites(movieId)
        }
    }
}
