package petrov.ivan.tmdb.ui.popularMovies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.service.TmdbApi

class MovieRepository(private val movieService: TmdbApi) {
    private val NETWORK_PAGE_SIZE = 20

    fun getSearchResultStream(query: String): Flow<PagingData<TmdbMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieService, query) }
        ).flow
    }
}
