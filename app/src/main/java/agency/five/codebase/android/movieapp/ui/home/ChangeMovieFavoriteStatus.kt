package agency.five.codebase.android.movieapp.ui.home

fun changeMovieFavoriteStatus(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    movieId: Int
): HomeMovieCategoryViewState {
    val selectedMovie = homeMovieCategoryViewState.movies.first { movie -> movie.id == movieId }
    val movieCardViewState =
        selectedMovie.movieCardViewState.copy(
            isFavorite = !selectedMovie.movieCardViewState.isFavorite
        )
    val movies = homeMovieCategoryViewState.movies.map {
        if (movieId == it.id) it.copy(movieCardViewState = movieCardViewState) else it
    }
    return homeMovieCategoryViewState.copy(movies = movies)
}
