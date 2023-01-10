package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class ActorCardViewState(
    val imageUrl: String?,
    val name: String,
    val character: String,
)

@Composable
fun ActorCard(
    actorCardViewState: ActorCardViewState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                contentDescription = "${actorCardViewState.name} profile image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.actor_card_image_height)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = actorCardViewState.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.medium_spacing),
                        top = dimensionResource(id = R.dimen.small_spacing),
                        end = dimensionResource(id = R.dimen.multi_large_spacing)
                    ),
            )
            Text(
                text = actorCardViewState.character,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.medium_spacing),
                        end = dimensionResource(id = R.dimen.medium_spacing),
                        bottom = dimensionResource(id = R.dimen.medium_spacing)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActorCardPreview() {
    MovieAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            ActorCard(
                actorCardViewState = ActorCardViewState(
                    imageUrl = "https://www.themoviedb.org/t/p/w200/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg",
                    name = "Robert Downey Jr.",
                    character = "Tony Stark/Iron Man"
                ),
                Modifier
                    .width(130.dp)
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(percent = 10))
            )
        }
    }
}
