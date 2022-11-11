package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.home.*
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MoviesWithinCategory(
    headingName: String,
    categoryViewState: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: (Int) -> Unit,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onFavoriteClick: (MovieCategoryLabelViewState, HomeMovieViewState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Heading(
            text = headingName,
            Modifier.padding(end = dimensionResource(id = R.dimen.large_spacing))
        )
        LazyRow(
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.medium_spacing),
                bottom = dimensionResource(id = R.dimen.large_spacing)
            ),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.very_large_spacing))
        ) {
            items(items = categoryViewState.movieCategories) { category ->
                MovieCategoryLabel(
                    movieCategoryLabelViewState = category,
                    onSelected = { onCategoryClick(category) },
                )
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large_spacing)),
        ) {
            items(items = categoryViewState.movies) { movie ->
                MovieCard(
                    movieCardViewState = movie.movieCardViewState,
                    onClick = { onNavigateToMovieDetails(movie.id) },
                    onFavoriteClick = {
                        onFavoriteClick(
                            categoryViewState.movieCategories.first { it.isSelected },
                            movie
                        )
                    },
                    modifier = Modifier
                        .size(
                            width = dimensionResource(id = R.dimen.movie_card_category_width),
                            height = dimensionResource(id = R.dimen.movie_card_category_height)
                        )
                        .shadow(elevation = 1.dp, shape = RoundedCornerShape(percent = 10))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieWithinCategoryPreview() {
    var popularCategoryViewState by remember {
        mutableStateOf(
            HomeScreenMapperImpl().toHomeMovieCategoryViewState(
                listOf(
                    MovieCategory.POPULAR_STREAMING,
                    MovieCategory.POPULAR_ONTV,
                    MovieCategory.POPULAR_FORRENT,
                    MovieCategory.POPULAR_INTHREATRES
                ),
                MovieCategory.POPULAR_STREAMING,
                MoviesMock.getMoviesList()
            )
        )
    }
    MovieAppTheme {
        MoviesWithinCategory(
            headingName = "What's popular",
            categoryViewState = popularCategoryViewState,
            onNavigateToMovieDetails = { },
            onCategoryClick = { category ->
                popularCategoryViewState = changeCategory(popularCategoryViewState, category.itemId)
            },
            onFavoriteClick = { _, movie ->
                popularCategoryViewState =
                    changeMovieFavoriteStatus(popularCategoryViewState, movie)
            },
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
        )
    }
}
