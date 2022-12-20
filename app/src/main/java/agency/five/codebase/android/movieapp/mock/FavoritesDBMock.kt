package agency.five.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.*

object FavoritesDBMock {
    private val _favoriteIds = MutableStateFlow(MoviesMock.getMoviesList().filter { movie -> movie.isFavorite }
        .map { movie -> movie.id }.toSet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds

    fun insert(movieId: Int) {
        _favoriteIds.update { it + movieId }
    }

    fun delete(movieId: Int) {
        _favoriteIds.update { it - movieId }
    }
}
