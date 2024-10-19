package zelimkhan.magomadov.islamicquotes.ui.background

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap

data class BackgroundState(
    val quoteBackground: Bitmap = ImageBitmap(1, 1).asAndroidBitmap(),
)