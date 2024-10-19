package zelimkhan.magomadov.islamicquotes.ui.wallpaper

sealed interface WallpaperIntent {
    data object ChangeQuote : WallpaperIntent
    data object SetWallpaper : WallpaperIntent
}