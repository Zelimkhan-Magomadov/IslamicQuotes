package zelimkhan.magomadov.islamicquotes.domain.wallpaper

import android.graphics.Bitmap

interface WallpaperService {
    fun setLockScreenWallpaper(wallpaper: Bitmap)
}