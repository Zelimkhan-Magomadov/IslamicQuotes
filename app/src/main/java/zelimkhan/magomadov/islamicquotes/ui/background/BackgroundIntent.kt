package zelimkhan.magomadov.islamicquotes.ui.background

import android.graphics.Bitmap

sealed interface BackgroundIntent {
    data class SetColorBackground(val color: Int) : BackgroundIntent
    data class SetImageBackground(val bitmap: Bitmap) : BackgroundIntent
    data class LoadImage(val bitmap: Bitmap) : BackgroundIntent
    data object LoadLockScreenWallpaper : BackgroundIntent
}