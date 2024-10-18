package zelimkhan.magomadov.islamicquotes.data.quote.local

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zelimkhan.magomadov.islamicquotes.data.quote.QuoteDataSource

class QuoteLocalDataSource(
    private val context: Context
) : QuoteDataSource {

    companion object {
        private const val QUOTES_PREF_KEY = "quotes"
    }

    private val preferences = context.getSharedPreferences("quote_prefs", Context.MODE_PRIVATE)

    override suspend fun getRandomQuote(): QuoteLocalModel = withContext(Dispatchers.IO) {
        val quotes = when (val quotesJson = preferences.getString(QUOTES_PREF_KEY, null)) {
            "[]", null -> updateQuotesInPreferences()
            else -> Gson().fromJson(quotesJson, object : TypeToken<List<String>>() {}.type)
        }

        val quote = quotes.first()
        val updatedQuotes = quotes.drop(1)

        preferences.edit(commit = true) {
            putString(QUOTES_PREF_KEY, Gson().toJson(updatedQuotes))
        }

        return@withContext QuoteLocalModel.fromString(quote)
    }

    private fun updateQuotesInPreferences(): List<String> {
        val quotesFromJson = getQuotesFromJson()
        val shuffledQuotes = quotesFromJson.shuffled().map { it.toString() }

        preferences.edit(commit = true) {
            putString(QUOTES_PREF_KEY, Gson().toJson(shuffledQuotes))
        }

        return shuffledQuotes
    }

    private fun getQuotesFromJson(): List<QuoteLocalModel> {
        val jsonString = context.assets.open("quotes.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val quoteLocalModelListType = object : TypeToken<List<QuoteLocalModel>>() {}.type
        return gson.fromJson(jsonString, quoteLocalModelListType)
    }
}