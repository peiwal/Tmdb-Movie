package petrov.ivan.tmdb

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import petrov.ivan.tmdb.data.TmdbMovieResponse
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.ui.search.SearchViewModel
import retrofit2.Response


private open class TmdbApiImpl: TmdbApi {
    var countCallLoad: Int = 0

    override suspend fun getPopularMovie(language: String, page: Int): TmdbMovieResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieByQuery(
        query: String,
        language: String
    ): Response<TmdbMovieResponse> {
        countCallLoad++
        return Response.success(TmdbMovieResponse(listOf(), 0, 0))
    }
}

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(28))
class TestDebounceRobolectric {

    private val restApi = TmdbApiImpl()
    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun searchDebounce() = runBlocking {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val searchViewModel = SearchViewModel(restApi, testDispatcher, application = application)

        val TEXT_TO_LOAD = "text_to_load"
        searchViewModel.searchText.value = TEXT_TO_LOAD
        searchViewModel.searchText.value = TEXT_TO_LOAD
        searchViewModel.searchText.value = TEXT_TO_LOAD

        testDispatcher.runBlockingTest {
            delay(SearchViewModel.DEBOUNCE)
            Assert.assertEquals(1, restApi.countCallLoad)
        }
    }
}