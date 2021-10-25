package petrov.ivan.tmdb

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class TestResourceProvider {

    @Test
    fun date_presents_correctly() {
        val context = RuntimeEnvironment.application;
        Assert.assertEquals("ru-RU", context.getString(R.string.response_language))
    }
}