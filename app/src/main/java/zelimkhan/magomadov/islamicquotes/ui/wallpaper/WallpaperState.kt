package zelimkhan.magomadov.islamicquotes.ui.wallpaper

import android.graphics.Bitmap

data class WallpaperState(
    val wallpaper: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
)
