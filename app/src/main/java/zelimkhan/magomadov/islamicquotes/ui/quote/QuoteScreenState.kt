package zelimkhan.magomadov.islamicquotes.ui.quote

import android.graphics.Bitmap

data class QuoteScreenState(
    val quoteImage: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val isNasheedMuted: Boolean = false
)
