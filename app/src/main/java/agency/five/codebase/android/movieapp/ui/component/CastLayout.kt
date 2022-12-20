package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.moviedetails.ActorViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.mapper.MovieDetailsMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CastLayout(cast: List<ActorViewState>, modifier: Modifier = Modifier) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large_spacing)),
        modifier = modifier
    ) {
        items(
            items = cast,
            key = { cast -> cast.id }
        ) { actor ->
            ActorCard(
                actorCardViewState = actor.actorCardViewState,
                Modifier
                    .width(dimensionResource(id = R.dimen.actor_card_width))
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(percent = 10))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CastLayoutPreview() {
    val movieDetailsViewState by remember {
        mutableStateOf(
            MovieDetailsMapperImpl().toMovieDetailsViewState(
                MoviesMock.getMovieDetails()
            )
        )
    }
    MovieAppTheme {
        CastLayout(
            cast = movieDetailsViewState.cast,
            modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)
        )
    }
}
