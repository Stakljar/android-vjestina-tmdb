package agency.five.codebase.android.movieapp.ui.favorites

fun onFavoriteButtonClick(
    favoritesViewState: FavoritesViewState,
    favoritesMovieViewState: FavoritesMovieViewState
): FavoritesViewState {
    val movieCardViewState =
        favoritesViewState.favoritesMovieViewStates.first { it.id == favoritesMovieViewState.id }.movieCardViewState.copy()
    val newMovieCardViewState =
        movieCardViewState.copy(isFavorite = !movieCardViewState.isFavorite)
    val favoritesMovieViewStates = favoritesViewState.favoritesMovieViewStates.map {
        if (it.id == favoritesMovieViewState.id) it.copy(movieCardViewState = newMovieCardViewState) else it.copy()
    }
    return favoritesViewState.copy(favoritesMovieViewStates = favoritesMovieViewStates)
}
