package agency.five.codebase.android.movieapp.model

import agency.five.codebase.android.movieapp.R
import androidx.annotation.StringRes

enum class MovieCategory(@StringRes val textRes: Int) {
    POPULAR_STREAMING(R.string.streaming),
    POPULAR_ON_TV(R.string.on_tv),
    POPULAR_FOR_RENT(R.string.for_rent),
    POPULAR_IN_THEATRES(R.string.in_theatres),
    NOW_PLAYING_MOVIES(R.string.movies),
    NOW_PLAYING_TV(R.string.tv),
    UPCOMING_TODAY(R.string.today),
    UPCOMING_THIS_WEEK(R.string.this_week)
}
