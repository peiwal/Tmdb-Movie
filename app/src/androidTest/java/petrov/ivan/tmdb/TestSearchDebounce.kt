package petrov.ivan.tmdb

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import petrov.ivan.tmdb.service.TmdbApi
import petrov.ivan.tmdb.ui.search.SearchViewModel
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class TestSearchDebounce {

    @Mock
    private lateinit var tmdbApi: TmdbApi
    @Mock
    private lateinit var application: Application

    private lateinit var searchViewModel: SearchViewModel

    private var countCallLoad: Int = 0
    private val countDown = CountDownLatch(1)

    private val TEXT_TO_LOAD = "text_to_load"


    @Before
    fun init() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        `when`(application.resources).thenReturn(context.resources)

        searchViewModel = SearchViewModel(tmdbApi, application = application)

        CoroutineScope(Dispatchers.Main).launch {
            `when`(tmdbApi.getMovieByQuery(TEXT_TO_LOAD, context.resources.getString(R.string.response_language)))
                .then {
                    countCallLoad++
                    countDown.countDown()}
        }
    }


    @Test
    fun searchDebounce() {
        searchViewModel.searchText.value = TEXT_TO_LOAD
        searchViewModel.searchText.value = TEXT_TO_LOAD
        searchViewModel.searchText.value = TEXT_TO_LOAD

        countDown.await(SearchViewModel.DEBOUNCE * 2, TimeUnit.MILLISECONDS)
        Assert.assertEquals(1, countCallLoad)
    }
}
