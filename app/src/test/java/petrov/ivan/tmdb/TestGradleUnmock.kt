package petrov.ivan.tmdb

import android.text.Editable
import org.junit.Assert
import org.junit.Test

class TestGradleUnmock {
    @Test
    fun testEditable() {
        val e = Editable.Factory.getInstance().newEditable("Hello World")
        e.append("!!!!")
        Assert.assertEquals("Hello World!!!!", e.toString())
    }
}