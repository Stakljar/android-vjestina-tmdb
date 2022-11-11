package agency.five.codebase.android.movieapp.ui.home

fun changeMovieFavoriteStatus(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    movie: HomeMovieViewState
): HomeMovieCategoryViewState {
    val movieCardViewState =
        movie.movieCardViewState.copy(isFavorite = !movie.movieCardViewState.isFavorite)
    val movies = homeMovieCategoryViewState.movies.map {
        if (movie == it) movie.copy(movieCardViewState = movieCardViewState) else it
    }
    return homeMovieCategoryViewState.copy(movies = movies)
}
