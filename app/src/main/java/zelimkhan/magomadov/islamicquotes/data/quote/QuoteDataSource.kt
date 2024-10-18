package zelimkhan.magomadov.islamicquotes.data.quote

import zelimkhan.magomadov.islamicquotes.data.quote.local.QuoteLocalModel

interface QuoteDataSource {
    suspend fun getRandomQuote(): QuoteLocalModel
}