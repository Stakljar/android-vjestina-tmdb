package agency.five.codebase.android.movieapp.model

import agency.five.codebase.android.movieapp.R
import androidx.annotation.StringRes

enum class MovieCategory(@StringRes val textRes: Int) {
    POPULAR_STREAMING(R.string.streaming),
    POPULAR_ONTV(R.string.on_tv),
    POPULAR_FORRENT(R.string.for_rent),
    POPULAR_INTHREATRES(R.string.in_theatres),
    NOWPLAYING_MOVIES(R.string.movies),
    NOWPLAYING_TV(R.string.tv),
    UPCOMING_TODAY(R.string.today),
    UPCOMING_THISWEEK(R.string.this_week)
}
