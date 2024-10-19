package zelimkhan.magomadov.islamicquotes.data.background

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zelimkhan.magomadov.islamicquotes.ui.core.getScreenSize

class ColorBackgroundGenerator(
    private val context: Context
) {
    suspend operator fun invoke(color: Int): Bitmap = withContext(Dispatchers.Default) {
        val screenSize = context.getScreenSize()

        Bitmap.createBitmap(
            screenSize.width,
            screenSize.height,
            Bitmap.Config.ARGB_8888
        ).apply {
            eraseColor(color)
        }
    }
}