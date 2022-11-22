package agency.five.codebase.android.movieapp.ui.favorites

fun onFavoriteButtonClick(
    favoritesViewState: FavoritesViewState,
    favoritesMovieId: Int
): FavoritesViewState {
    val movieCardViewState =
        favoritesViewState.favoritesMovieViewStates.first { it.id == favoritesMovieId }.movieCardViewState.copy()
    val newMovieCardViewState =
        movieCardViewState.copy(isFavorite = !movieCardViewState.isFavorite)
    val favoritesMovieViewStates = favoritesViewState.favoritesMovieViewStates.map {
        if (it.id == favoritesMovieId) it.copy(movieCardViewState = newMovieCardViewState) else it.copy()
    }
    return favoritesViewState.copy(favoritesMovieViewStates = favoritesMovieViewStates)
}
