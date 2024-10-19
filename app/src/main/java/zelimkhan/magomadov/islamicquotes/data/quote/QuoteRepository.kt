package zelimkhan.magomadov.islamicquotes.data.quote

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import zelimkhan.magomadov.islamicquotes.domain.quote.Quote

class QuoteRepository(
    private val quoteLocalDataSource: QuoteDataSource
) {
    private val _currentQuoteStream = MutableStateFlow<Quote?>(null)
    val currentQuoteStream = _currentQuoteStream.filterNotNull()

    suspend fun changeQuote() {
        _currentQuoteStream.value = quoteLocalDataSource.getRandomQuote()
    }
}