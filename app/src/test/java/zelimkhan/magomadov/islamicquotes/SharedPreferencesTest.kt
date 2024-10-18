package zelimkhan.magomadov.islamicquotes

import android.content.Context
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesTest {
    @Test
    fun testSharedPreferences() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val sharedPreferences = context.getSharedPreferences("test_pref", Context.MODE_PRIVATE)

        sharedPreferences.edit(commit = true) {
            putStringSet("set", setOf("4", "2", "1"))
        }

        val set = sharedPreferences.getStringSet("set", emptySet())

        println("====================================================")
        println(set)
        println("====================================================")
    }
}