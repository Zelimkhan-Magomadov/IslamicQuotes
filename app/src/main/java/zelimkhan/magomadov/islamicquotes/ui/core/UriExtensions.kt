package zelimkhan.magomadov.islamicquotes.ui.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

fun Uri?.getBitmap(context: Context): Bitmap? {
    if (this == null) return null
    val inputStream = context.contentResolver.openInputStream(this)
    return BitmapFactory.decodeStream(inputStream)
}
