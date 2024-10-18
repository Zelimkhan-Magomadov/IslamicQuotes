package zelimkhan.magomadov.islamicquotes.domain.quote

interface QuoteRepository {
    suspend fun getRandomQuote(): Quote
}