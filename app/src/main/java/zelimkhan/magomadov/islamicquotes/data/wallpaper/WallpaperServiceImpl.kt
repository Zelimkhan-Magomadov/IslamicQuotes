package zelimkhan.magomadov.islamicquotes.data.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import zelimkhan.magomadov.islamicquotes.domain.wallpaper.WallpaperService

class WallpaperServiceImpl(
    private val context: Context,
) : WallpaperService {
    override fun setLockScreenWallpaper(wallpaper: Bitmap) {
        WallpaperManager
            .getInstance(context)
            .setBitmap(
                wallpaper,
                null,
                true,
                WallpaperManager.FLAG_LOCK
            )
    }
}