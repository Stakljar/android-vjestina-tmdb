package agency.five.codebase.android.movieapp.ui.home

fun changeCategory(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    itemId: Int
): HomeMovieCategoryViewState {
    val movieCategories = homeMovieCategoryViewState.movieCategories.map {
        it.copy(isSelected = itemId == it.itemId)
    }
    return homeMovieCategoryViewState.copy(movieCategories = movieCategories)
}
