package zelimkhan.magomadov.islamicquotes.domain.wallpaper

import android.graphics.Bitmap
import zelimkhan.magomadov.islamicquotes.domain.quote.GetQuoteUseCase

class GenerateWallpaperUseCase(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val wallpaperGenerator: WallpaperGenerator
) {
    suspend operator fun invoke(): Bitmap {
        val quote = getQuoteUseCase()
        return wallpaperGenerator.generateWallpaper(quote)
    }
}