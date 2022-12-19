package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import agency.five.codebase.android.movieapp.ui.home.HomeViewModel
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
fun MoviesWithinCategory(
    headingName: String,
    categoryViewState: HomeMovieCategoryViewState,
    onNavigateToMovieDetails: (Int) -> Unit,
    onCategoryClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
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
                    onSelected = { onCategoryClick(category.itemId) },
                )
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large_spacing)),
        ) {
            items(
                items = categoryViewState.movies,
                key = { movie -> movie.id }
            ) { movie ->
                MovieCard(
                    movieCardViewState = movie.movieCardViewState,
                    onClick = { onNavigateToMovieDetails(movie.id) },
                    onFavoriteClick = {
                        onFavoriteClick(
                            movie.id
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
    val viewModel: HomeViewModel by getViewModel()
    val popularCategoryViewState by viewModel.popularMoviesViewState.collectAsState()

    MovieAppTheme {
        MoviesWithinCategory(
            headingName = "What's popular",
            categoryViewState = popularCategoryViewState,
            onNavigateToMovieDetails = { },
            onCategoryClick = viewModel::changeCategory,
            onFavoriteClick = viewModel::toggleFavorite,
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
        )
    }
}
