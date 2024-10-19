package zelimkhan.magomadov.islamicquotes.data.quote

import zelimkhan.magomadov.islamicquotes.domain.quote.Quote

interface QuoteDataSource {
    suspend fun getRandomQuote(): Quote
}