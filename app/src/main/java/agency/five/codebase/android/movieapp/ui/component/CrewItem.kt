package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

data class CrewItemViewState(
    val name: String,
    val job: String
)

@Composable
fun CrewItem(
    crewItemViewState: CrewItemViewState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = crewItemViewState.name,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.very_small_spacing)),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        Text(
            text = crewItemViewState.job,
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CrewItemPreview() {
    MovieAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            CrewItem(
                crewItemViewState = CrewItemViewState(
                    name = "John Favreau",
                    job = "Director"
                )
            )
        }
    }
}
