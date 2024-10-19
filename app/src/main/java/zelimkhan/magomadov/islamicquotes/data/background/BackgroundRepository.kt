package zelimkhan.magomadov.islamicquotes.data.background

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.withContext

class BackgroundRepository(
    private val context: Context,
    private val colorBackgroundGenerator: ColorBackgroundGenerator
) {
    private val _backgroundStream = MutableStateFlow<Bitmap?>(null)

    companion object {
        private const val FILE_NAME = "background.png"
    }

    suspend fun getBackgroundStream(): Flow<Bitmap> = withContext(Dispatchers.IO) {
        try {
            _backgroundStream.value = context.openFileInput(FILE_NAME).use {
                BitmapFactory.decodeStream(it)
            }
        } catch (e: Exception) {
            _backgroundStream.value = colorBackgroundGenerator(Color.BLACK)
        }
        _backgroundStream.filterNotNull()
    }

    suspend fun saveImageBackground(bitmap: Bitmap) {
        saveBackground(bitmap)
        _backgroundStream.value = bitmap
    }

    suspend fun saveColorBackground(color: Int) {
        val colorBackground = colorBackgroundGenerator(color)
        _backgroundStream.value = colorBackground
        saveBackground(colorBackground)
    }

    private suspend fun saveBackground(bitmap: Bitmap): Unit = withContext(Dispatchers.IO) {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
    }
}