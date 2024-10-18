package zelimkhan.magomadov.islamicquotes.data.quote

import zelimkhan.magomadov.islamicquotes.data.quote.local.asDomainModel
import zelimkhan.magomadov.islamicquotes.domain.quote.Quote
import zelimkhan.magomadov.islamicquotes.domain.quote.QuoteRepository

class QuoteRepositoryImpl(
    private val quoteLocalDataSource: QuoteDataSource
) : QuoteRepository {

    override suspend fun getRandomQuote(): Quote {
        return quoteLocalDataSource.getRandomQuote().asDomainModel()
    }
}