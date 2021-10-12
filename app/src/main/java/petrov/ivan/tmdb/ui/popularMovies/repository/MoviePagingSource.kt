package petrov.ivan.tmdb.ui.popularMovies.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import petrov.ivan.tmdb.data.TmdbMovie
import petrov.ivan.tmdb.service.TmdbApi
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(private val movieService: TmdbApi, private val responseLanguage: String) : PagingSource<Int, TmdbMovie>() {
    private val STARTING_PAGE_INDEX = 1

    override fun getRefreshKey(state: PagingState<Int, TmdbMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TmdbMovie> {
        return try {
            val nextPage = params.key ?: STARTING_PAGE_INDEX
            val response = movieService.getPopularMovie(responseLanguage, nextPage)

            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPage == STARTING_PAGE_INDEX) null else nextPage - 1,
                nextKey = if (nextPage < response.totalPages) response.page.plus(1) else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
