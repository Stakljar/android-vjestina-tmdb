package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.moviedetails.CrewmanViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CrewLayout(crew: List<CrewmanViewState>, modifier: Modifier = Modifier) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.vertical_movie_card_favorites_spacing)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.very_large_spacing)),
        modifier = modifier
    ) {
        items(items = crew) { crewman ->
            CrewItem(crewItemViewState = crewman.crewItemViewState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CrewLayoutPreview() {
    val movieDetailsViewState by remember {
        mutableStateOf(
            MovieDetailsMapperImpl().toMovieDetailsViewState(
                MoviesMock.getMovieDetails()
            )
        )
    }
    MovieAppTheme {
        CrewLayout(
            crew = movieDetailsViewState.crew, modifier = Modifier
                .height(120.dp)
                .padding(start = 15.dp, end = 15.dp, bottom = 20.dp)
        )
    }
}
