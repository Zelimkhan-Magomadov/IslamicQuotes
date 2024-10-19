package zelimkhan.magomadov.islamicquotes.data.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.ParcelFileDescriptor

class WallpaperService(
    private val context: Context,
) {
    fun setLockScreenWallpaper(wallpaper: Bitmap) {
        WallpaperManager
            .getInstance(context)
            .setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
    }

    fun getCurrentLockScreenWallpaper(): Bitmap {
        return WallpaperManager
            .getInstance(context)
            .getWallpaperFile(WallpaperManager.FLAG_LOCK)
            .let {
                val inputStream = ParcelFileDescriptor.AutoCloseInputStream(it)
                BitmapFactory.decodeStream(inputStream)
            }
    }
}