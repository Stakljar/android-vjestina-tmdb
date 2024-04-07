package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserScoreProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Color(color = 0xFFd4ffd4).copy(alpha = 0.3F),
                startAngle = (progress * 360) - 90,
                sweepAngle = (1 - progress) * 360,
                style = Stroke(
                    cap = StrokeCap.Round,
                    width = 6F
                ),
                useCenter = false,
            )
            drawArc(
                color = Color.Green,
                startAngle = -90F,
                sweepAngle = progress * 360,
                style = Stroke(
                    cap = StrokeCap.Round,
                    width = 6F,
                ),
                useCenter = false,
            )
        }
        Text(
            text = (progress * 10).toString(),
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserScoreProgressBarPreview() {
    MovieAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            UserScoreProgressBar(
                progress = 0.75F,
                Modifier
                    .size(40.dp)
                    .padding(2.dp)
            )
        }
    }
}
