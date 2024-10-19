package zelimkhan.magomadov.islamicquotes.data.wallpaper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.Layout.Alignment.ALIGN_NORMAL
import android.text.StaticLayout
import android.text.TextPaint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.res.ResourcesCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zelimkhan.magomadov.islamicquotes.R
import zelimkhan.magomadov.islamicquotes.domain.quote.Quote

class WallpaperGenerator(
    private val context: Context
) {
    suspend operator fun invoke(
        quote: Quote,
        backgroundImage: Bitmap
    ): Bitmap = withContext(Dispatchers.Default) {
        val bitmap = backgroundImage.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(bitmap)

        val arabicTextPaint = TextPaint().apply {
            color = Color.White.toArgb()
            textSize = bitmap.height * 0.035f
            isAntiAlias = true
            typeface = ResourcesCompat.getFont(context, R.font.scheherazade_new_bold)
        }

        val quotePaint = TextPaint().apply {
            color = Color.White.toArgb()
            textSize = bitmap.height * 0.023f
            isAntiAlias = true
            typeface = ResourcesCompat.getFont(context, R.font.comfortaa_semi_bold)
        }

        val sourcePaint = TextPaint().apply {
            color = Color.White.toArgb()
            textSize = bitmap.height * 0.018f
            isAntiAlias = true
            textAlign = Paint.Align.RIGHT
            typeface = ResourcesCompat.getFont(context, R.font.comfortaa_semi_bold)
        }

        val padding = (bitmap.width * 0.125f).toInt()
        val availableWidth = bitmap.width - 2 * padding

        val arabicTextPosY = bitmap.height * 0.32f
        val arabicTextLayout = getStaticLayout(quote.arabicText, arabicTextPaint, availableWidth)
        drawTextInCanvas(canvas, padding.toFloat(), arabicTextPosY, arabicTextLayout)
        val arabicTextHeight = arabicTextLayout.height

        val quotePosY = arabicTextPosY + arabicTextHeight + bitmap.height * 0.025f
        val quoteLayout = getStaticLayout(quote.quote, quotePaint, availableWidth)
        drawTextInCanvas(canvas, padding.toFloat(), quotePosY, quoteLayout)
        val quoteHeight = quoteLayout.height

        val sourcePosY = quotePosY + quoteHeight + bitmap.height * 0.025f
        val sourceLayout = getStaticLayout(quote.source, sourcePaint, availableWidth, ALIGN_NORMAL)
        drawTextInCanvas(canvas, (bitmap.width - padding).toFloat(), sourcePosY, sourceLayout)

        return@withContext bitmap
    }

    private fun getStaticLayout(
        text: String,
        textPaint: TextPaint,
        availableWidth: Int,
        alignment: Layout.Alignment = Layout.Alignment.ALIGN_CENTER
    ): StaticLayout {
        return StaticLayout.Builder
            .obtain(text, 0, text.length, textPaint, availableWidth)
            .setAlignment(alignment)
            .setLineSpacing(1.0f, 1.0f)
            .build()
    }

    private fun drawTextInCanvas(
        canvas: Canvas,
        posX: Float,
        posY: Float,
        textLayout: StaticLayout,
    ) {
        canvas.save()
        canvas.translate(posX, posY)
        textLayout.draw(canvas)
        canvas.restore()
    }
}