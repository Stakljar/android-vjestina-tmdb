package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class ActorCardViewState(
    val imageUrl: String,
    val name: String,
    val character: String,
)

@Composable
fun ActorCard(
    actorCardViewState: ActorCardViewState,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(10),
        elevation = 10.dp,
        modifier = modifier
    ) {
        Column {
            AsyncImage(
                model = actorCardViewState.imageUrl,
                contentDescription = "${actorCardViewState.name} profile image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
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
                imageUrl = "https://assets-global.website-files.com/5f3e7f710351ee0491efb21b/6019d6b44b2ae" +
                        "361ce81f1b6_Screen%20Shot%202021-02-02%20at%2014.48.11.png",
                name = "Robert Downey Jr.",
                character = "Tony Stark/Iron Man"
            ),
            Modifier.width(130.dp)
        )
    }
}
