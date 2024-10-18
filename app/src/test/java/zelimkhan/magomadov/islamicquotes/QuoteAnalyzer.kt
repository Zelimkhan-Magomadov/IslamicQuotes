package zelimkhan.magomadov.islamicquotes

import zelimkhan.magomadov.islamicquotes.data.quote.local.QuoteLocalModel
import java.util.Locale

class QuoteAnalyzer {

    fun findDuplicateQuotes(quoteLocalModels: List<QuoteLocalModel>): List<Set<String>> {
        val visited = mutableSetOf<Int>()
        val duplicatesList = mutableListOf<Set<String>>()

        for (i in quoteLocalModels.indices) {
            if (i in visited) continue

            val currentQuote = quoteLocalModels[i].quote
            val duplicates = mutableSetOf(currentQuote)

            for (j in i + 1 until quoteLocalModels.size) {
                if (j in visited) continue

                val otherQuote = quoteLocalModels[j].quote
                if (areQuotesSimilar(currentQuote, otherQuote)) {
                    duplicates.add(otherQuote)
                    visited.add(j)
                }
            }

            if (duplicates.size > 1) {
                duplicatesList.add(duplicates)
                visited.add(i)
            }
        }

        return duplicatesList
    }

    private fun areQuotesSimilar(
        quote1: String,
        quote2: String,
        threshold: Double = 0.5
    ): Boolean {
        val normalizedQuote1 = normalizeString(quote1)
        val normalizedQuote2 = normalizeString(quote2)

        val similarity = cosineSimilarity(normalizedQuote1, normalizedQuote2)

        return similarity >= threshold
    }

    private fun normalizeString(input: String): String {
        return input
            .lowercase(Locale.getDefault())
            .replace(Regex("[^a-zа-я0-9 ]"), "")
            .trim()
            .replace(Regex("\\s+"), " ")
    }

    private fun cosineSimilarity(str1: String, str2: String): Double {
        val words1 = str1.split(" ").groupingBy { it }.eachCount()
        val words2 = str2.split(" ").groupingBy { it }.eachCount()

        val commonWords = words1.keys.intersect(words2.keys)

        val dotProduct = commonWords.sumOf { words1[it]!!.toDouble() * words2[it]!!.toDouble() }
        val magnitude1 = kotlin.math.sqrt(words1.values.sumOf { it.toDouble() * it })
        val magnitude2 = kotlin.math.sqrt(words2.values.sumOf { it.toDouble() * it })

        return if (magnitude1 == 0.0 || magnitude2 == 0.0) 0.0 else dotProduct / (magnitude1 * magnitude2)
    }

    private fun jaccardSimilarity(str1: String, str2: String): Double {
        val set1 = str1.split(" ").toSet()
        val set2 = str2.split(" ").toSet()

        val intersection = set1.intersect(set2).size.toDouble()
        val union = set1.union(set2).size.toDouble()

        return if (union == 0.0) 0.0 else intersection / union
    }
}