package agency.five.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.*

object FavoritesDBMock {
    private val _favoriteIds = MutableStateFlow(MoviesMock.getMoviesList().filter { movie -> movie.isFavorite }
        .map { movie -> movie.id }.toSet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds

    fun insert(movieId: Int) {
        if (favoriteIds.value.contains(movieId)) {
            return
        }
        val temporarySet = favoriteIds.value.toMutableSet()
        temporarySet.add(movieId)
        _favoriteIds.value = temporarySet.toSet()
    }

    fun delete(movieId: Int) {
        val temporarySet = favoriteIds.value.toMutableSet()
        temporarySet.remove(movieId)
        _favoriteIds.value = temporarySet.toSet()
    }
}
