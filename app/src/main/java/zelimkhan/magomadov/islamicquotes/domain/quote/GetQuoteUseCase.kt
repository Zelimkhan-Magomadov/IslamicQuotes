package zelimkhan.magomadov.islamicquotes.domain.quote

class GetQuoteUseCase(
    private val quoteRepository: QuoteRepository
) {
    suspend operator fun invoke(): Quote {
        return quoteRepository.getRandomQuote()
    }
}