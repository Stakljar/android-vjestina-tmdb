package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
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
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = actorCardViewState.name,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(start = 10.dp, top = 6.dp, end = 30.dp, bottom = 2.dp),
                fontSize = 12.sp
            )
            Text(
                text = actorCardViewState.character,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                fontSize = 10.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActorCardPreview() {
    MovieAppTheme {
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
