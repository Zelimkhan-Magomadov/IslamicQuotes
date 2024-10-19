package zelimkhan.magomadov.islamicquotes.ui.background.components.crop

import android.graphics.Bitmap
import android.graphics.Rect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CropController {
    private var bitmap: ImageBitmap? = null
    private var screenWidth = 0
    private var screenHeight = 0
    private var offsetX = 0
    private var offsetY = 0

    internal fun updateState(
        bitmap: ImageBitmap,
        screenWidth: Int,
        screenHeight: Int,
        offsetX: Float,
        offsetY: Float
    ) {
        this.bitmap = bitmap
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight
        this.offsetX = offsetX.toInt()
        this.offsetY = offsetY.toInt()
    }

    suspend fun cropImage(): Bitmap? {
        val currentBitmap = bitmap ?: return null

        return cropImage(
            image = currentBitmap.asAndroidBitmap(),
            offsetX = offsetX,
            offsetY = offsetY,
            width = screenWidth,
            height = screenHeight
        )
    }

    private suspend fun cropImage(
        image: Bitmap,
        offsetX: Int,
        offsetY: Int,
        width: Int,
        height: Int,
    ): Bitmap = withContext(Dispatchers.Default) {
        val mutableBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = android.graphics.Canvas(mutableBitmap)

        canvas.drawBitmap(
            image,
            Rect(offsetX, offsetY, offsetX + width, offsetY + height),
            Rect(0, 0, width, height),
            null
        )

        return@withContext mutableBitmap
    }
}

@Composable
fun rememberCropController(): CropController {
    return remember { CropController() }
}