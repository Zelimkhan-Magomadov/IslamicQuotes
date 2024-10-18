package zelimkhan.magomadov.islamicquotes.ui.quote

sealed interface QuoteIntent {
    data object ChangeQuote: QuoteIntent
    data object ToggleNasheedMute: QuoteIntent
    data object NasheedPause: QuoteIntent
    data object NasheedResume: QuoteIntent
}