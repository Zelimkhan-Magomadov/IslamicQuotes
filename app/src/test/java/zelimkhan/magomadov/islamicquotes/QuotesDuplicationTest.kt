package zelimkhan.magomadov.islamicquotes

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import zelimkhan.magomadov.islamicquotes.data.quote.local.QuoteLocalModel
import java.io.File

class QuotesDuplicationTest {
    private lateinit var quoteAnalyzer: QuoteAnalyzer
    private lateinit var quoteLocalModels: List<QuoteLocalModel>

    @Before
    fun setUp() {
        quoteAnalyzer = QuoteAnalyzer()
        quoteLocalModels = loadQuotesFromJson(
            "C:\\Users\\Zelimkhan\\AndroidStudioProjects\\IslamicQuotes\\app\\src\\main\\assets\\quotes.json"
        )
    }

    private fun loadQuotesFromJson(filePath: String): List<QuoteLocalModel> {
        val jsonString = File(filePath).bufferedReader().use { it.readText() }
        val quoteLocalModelListType = object : TypeToken<List<QuoteLocalModel>>() {}.type
        return Gson().fromJson(jsonString, quoteLocalModelListType)
    }

    @Test
    fun `should not find duplicates when quotes are different`() {
        val duplicates = quoteAnalyzer.findDuplicateQuotes(quoteLocalModels)

        println("===============================================")
        println("Количество найденных дубликатов: ${duplicates.size}")

        duplicates.forEachIndexed { index, group ->
            println("Группа ${index + 1}:")
            group.forEach { quote -> println("- $quote") }
            println()
        }

        println("===============================================")

        assertTrue(duplicates.isEmpty())
    }
}