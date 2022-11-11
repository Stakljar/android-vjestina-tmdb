package agency.five.codebase.android.movieapp.ui.home

fun changeCategory(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    itemId: Int
): HomeMovieCategoryViewState {
    val movieCategories = homeMovieCategoryViewState.movieCategories.map {
        if (itemId == it.itemId) it.copy(isSelected = true) else if (it.isSelected)
            it.copy(isSelected = false) else it.copy()
    }
    return homeMovieCategoryViewState.copy(movieCategories = movieCategories)
}
