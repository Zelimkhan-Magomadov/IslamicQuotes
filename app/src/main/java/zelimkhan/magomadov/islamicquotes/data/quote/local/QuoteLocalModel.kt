package zelimkhan.magomadov.islamicquotes.data.quote.local

import zelimkhan.magomadov.islamicquotes.domain.quote.Quote

data class QuoteLocalModel(
    val arabicText: String,
    val quote: String,
    val source: String
) {
    override fun toString(): String {
        return "$arabicText@$quote@$source"
    }

    companion object {
        fun fromString(string: String): QuoteLocalModel {
            val parts = string.split("@")
            return QuoteLocalModel(
                arabicText = parts[0],
                quote = parts[1],
                source = parts[2]
            )
        }
    }
}

fun QuoteLocalModel.asDomainModel(): Quote {
    return Quote(
        arabicText = arabicText,
        quote = quote,
        source = source
    )
}