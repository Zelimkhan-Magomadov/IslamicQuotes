package zelimkhan.magomadov.islamicquotes.ui

sealed interface MainIntent {
    data object ToggleNasheedMute : MainIntent
    data object NasheedPause : MainIntent
    data object NasheedResume : MainIntent
}