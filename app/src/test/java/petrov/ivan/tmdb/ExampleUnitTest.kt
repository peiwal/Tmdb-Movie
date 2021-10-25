package petrov.ivan.tmdb

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import petrov.ivan.tmdb.ui.utils.parseMovieReleaseYear

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    @Test
    fun testDateReleaseFormatter() {
        assertEquals(parseMovieReleaseYear("2019-10-19"), "2019")
    }

    class TestClass(val message: String) {
        fun getCurrentMessage(value: String) = message
    }

    private val testClass: TestClass = TestClass("2")
    private val spy = spy(testClass)

    @Test
    fun testMockitoAnswer() {
        `when`(spy.getCurrentMessage(anyString()))
            .thenAnswer {
                return@thenAnswer if (it.getArgument<String>(0) == "2") {
                    "one"
                } else it.callRealMethod()
            }

        assertNotEquals("two", spy.getCurrentMessage("2"))
        assertEquals("2", spy.getCurrentMessage("3"))
    }
}
