package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class MovieCategoryLabelTextViewState

class MovieCategoryLabelTextViewStateDirect(val text: String) : MovieCategoryLabelTextViewState()

class MovieCategoryLabelTextViewStateReferenced(@StringRes val textRes: Int) :
    MovieCategoryLabelTextViewState()

data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState
)

@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .width(IntrinsicSize.Min)
        .clickable { onSelected() }
    ) {
        Text(
            text = when (movieCategoryLabelViewState.categoryText) {
                is MovieCategoryLabelTextViewStateDirect -> movieCategoryLabelViewState.categoryText.text
                is MovieCategoryLabelTextViewStateReferenced -> stringResource(id = movieCategoryLabelViewState.categoryText.textRes)
            },
            fontWeight = if (movieCategoryLabelViewState.isSelected) FontWeight.ExtraBold else FontWeight.Normal,
            style = MaterialTheme.typography.subtitle1,
        )
        if (movieCategoryLabelViewState.isSelected) {
            Divider(
                color = MaterialTheme.colors.onBackground,
                thickness = dimensionResource(id = R.dimen.divider_thickness),
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.very_small_spacing))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieCategoryLabelPreview() {
    var movieCategoryLabelViewState by remember {
        mutableStateOf(
            MovieCategoryLabelViewState(
                itemId = 0,
                isSelected = false,
                categoryText = MovieCategoryLabelTextViewStateDirect(text = "Movies")
            )
        )
    }
    MovieAppTheme {
        MovieCategoryLabel(
            movieCategoryLabelViewState = movieCategoryLabelViewState,
            onSelected = {
                movieCategoryLabelViewState = movieCategoryLabelViewState.copy(isSelected = true)
            }
        )
    }
}
