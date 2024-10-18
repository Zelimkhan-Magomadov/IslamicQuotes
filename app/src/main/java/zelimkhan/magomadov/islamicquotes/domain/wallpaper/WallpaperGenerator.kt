package zelimkhan.magomadov.islamicquotes.domain.wallpaper

import android.graphics.Bitmap
import zelimkhan.magomadov.islamicquotes.domain.quote.Quote

interface WallpaperGenerator {
    suspend fun generateWallpaper(quote: Quote): Bitmap
}