package agency.five.codebase.android.movieapp.ui.component

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
    isSelected: Boolean,
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
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal,
            fontSize = 12.sp,
        )
        if (isSelected) {
            Divider(
                color = MaterialTheme.colors.onBackground,
                thickness = 3.dp,
                modifier = Modifier.padding(top = 3.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieCategoryLabelPreview() {
    val movieCategoryLabelViewState = MovieCategoryLabelViewState(
        itemId = 0,
        isSelected = false,
        categoryText = MovieCategoryLabelTextViewStateDirect(text = "Movies")
    )
    var isSelected by remember { mutableStateOf(movieCategoryLabelViewState.isSelected) }
    MovieAppTheme {
        MovieCategoryLabel(
            movieCategoryLabelViewState = movieCategoryLabelViewState,
            isSelected = isSelected,
            onSelected = { isSelected = true }
        )
    }
}
