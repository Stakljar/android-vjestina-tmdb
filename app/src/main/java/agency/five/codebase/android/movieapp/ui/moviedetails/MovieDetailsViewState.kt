package agency.five.codebase.android.movieapp.ui.moviedetails

data class MovieDetailsViewState(
    val id: Int,
    val imageUrl: String?,
    val voteAverage: Float,
    val title: String,
    val overview: String,
    val isFavorite: Boolean,
    val crew: List<CrewmanViewState>,
    val cast: List<ActorViewState>,
)
