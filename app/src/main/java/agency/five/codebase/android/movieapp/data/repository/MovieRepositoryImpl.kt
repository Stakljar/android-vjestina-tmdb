package agency.five.codebase.android.movieapp.data.repository

import agency.five.codebase.android.movieapp.data.database.DbFavoriteMovie
import agency.five.codebase.android.movieapp.data.database.FavoriteMovieDao
import agency.five.codebase.android.movieapp.data.network.MovieService
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import android.os.DeadObjectException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import java.net.UnknownHostException

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: FavoriteMovieDao,
    private val bgDispatcher: CoroutineDispatcher
) : MovieRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val moviesByCategory: Map<MovieCategory, Flow<List<Movie>>> = MovieCategory.values()
        .associateWith { movieCategory ->
            flow {
                val movieResponse = when (movieCategory) {

                    MovieCategory.POPULAR_STREAMING -> {
                        movieService.fetchPopularMovies()
                    }
                    MovieCategory.POPULAR_ON_TV -> {
                        movieService.fetchTopRatedMovies()
                    }
                    MovieCategory.POPULAR_FOR_RENT -> {
                        movieService.fetchNowPlayingMovies()
                    }
                    MovieCategory.POPULAR_IN_THEATRES -> {
                        movieService.fetchUpcomingMovies()
                    }
                    MovieCategory.NOW_PLAYING_MOVIES -> {
                        movieService.fetchNowPlayingMovies()
                    }
                    MovieCategory.NOW_PLAYING_TV -> {
                        movieService.fetchTopRatedMovies()
                    }
                    MovieCategory.UPCOMING_TODAY -> {
                        movieService.fetchUpcomingMovies()
                    }
                    MovieCategory.UPCOMING_THIS_WEEK -> {
                        movieService.fetchTopRatedMovies()
                    }
                }
                emit(movieResponse.movies)
            }.flatMapLatest { apiMovies ->
                movieDao.favorites()
                    .map { favoriteMovies ->
                        apiMovies.map { apiMovie ->
                            apiMovie.toMovie(isFavorite = favoriteMovies.any { it.id == apiMovie.id })
                        }
                    }
            }.shareIn(
                scope = CoroutineScope(bgDispatcher + CoroutineExceptionHandler { _, throwable ->
                    throwable.printStackTrace()
                }),
                started = SharingStarted.WhileSubscribed(1000L),
                replay = 1,
            )
        }

    private val favorites = movieDao.favorites().map {
        it.map { dbFavoriteMovie ->
            Movie(
                id = dbFavoriteMovie.id,
                imageUrl = dbFavoriteMovie.posterUrl,
                title = "",
                overview = "",
                isFavorite = true,
            )
        }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher),
        started = SharingStarted.WhileSubscribed(1000L),
        replay = 1,
    )

    override fun movies(movieCategory: MovieCategory): Flow<List<Movie>> =
        moviesByCategory[movieCategory]!!

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun movieDetails(movieId: Int): Flow<MovieDetails> = flow {
        emit(movieService.fetchMovieDetails(movieId) to movieService.fetchMovieCredits(movieId))
    }.flatMapLatest { (apiMovieDetails, apiMovieCredits) ->
        movieDao.favorites()
            .map { favoriteMovies ->
                apiMovieDetails.toMovieDetails(
                    isFavorite = favoriteMovies.any { it.id == apiMovieDetails.id },
                    crew = apiMovieCredits.crew,
                    cast = apiMovieCredits.cast
                )
            }
    }.flowOn(bgDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = favorites

    override suspend fun addMovieToFavorites(movieId: Int) {
        val movie = findMovie(movieId)
        movieDao.insertMovie(
            DbFavoriteMovie(
                id = movie.id,
                posterUrl = movie.imageUrl
            )
        )
    }

    @OptIn(FlowPreview::class)
    private suspend fun findMovie(movieId: Int): Movie {
        return moviesByCategory.values.asFlow().flattenMerge()
            .filter { it -> it.firstOrNull { it.id == movieId } != null }
            .first().first { it.id == movieId }
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        movieDao.deleteMovie(movieId)
    }

    override suspend fun toggleFavorite(movieId: Int) {
        try {
            val movie = findMovie(movieId)
            if (movie.isFavorite) {
                removeMovieFromFavorites(movieId)
            } else {
                addMovieToFavorites(movieId)
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
        } catch (e: DeadObjectException) {
            e.printStackTrace()
        }
    }
}
