package agency.five.codebase.android.movieapp.ui.home.mapper

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelTextViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelTextViewStateReferenced
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieViewState

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeMovieCategoryViewState(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
        movies: List<Movie>
    ): HomeMovieCategoryViewState {
        return HomeMovieCategoryViewState(movieCategories.map { movieCategory ->
            MovieCategoryLabelViewState(
                itemId = movieCategory.ordinal, isSelected = movieCategory == selectedMovieCategory,
                categoryText = MovieCategoryLabelTextViewStateReferenced(movieCategory.textRes)
            )
        }, movies.map { movie ->
            HomeMovieViewState(
                id = movie.id,
                movieCardViewState = MovieCardViewState(
                    imageUrl = movie.imageUrl,
                    isFavorite = movie.isFavorite
                )
            )
        })
    }
}
