package zelimkhan.magomadov.islamicquotes.ui.background.components.crop

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.core.graphics.scale
import zelimkhan.magomadov.islamicquotes.R
import zelimkhan.magomadov.islamicquotes.ui.theme.IslamicQuotesTheme
import kotlin.math.absoluteValue

@Composable
fun CropImage(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int,
    controller: CropController
) {
    CropImage(
        modifier = modifier,
        image = ImageBitmap.imageResource(imageRes),
        controller = controller
    )
}

@Composable
fun CropImage(
    modifier: Modifier = Modifier,
    image: Bitmap,
    controller: CropController
) {
    CropImage(
        modifier = modifier,
        image = image.asImageBitmap(),
        controller = controller
    )
}

@Composable
fun CropImage(
    modifier: Modifier = Modifier,
    image: ImageBitmap,
    controller: CropController
) {
    var screenWidth = 0
    var screenHeight = 0

    var scaledImage = image

    var offsetX by remember(image) {
        mutableFloatStateOf(0f)
    }

    var offsetY by remember(image) {
        mutableFloatStateOf(0f)
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(image) {
                detectTransformGestures { centroid, pan, gestureZoom, rotation ->
                    offsetX = (offsetX + pan.x).coerceIn(
                        minimumValue = (size.width - scaledImage.width).toFloat(),
                        maximumValue = 0f
                    )

                    offsetY = (offsetY + pan.y).coerceIn(
                        minimumValue = (size.height - scaledImage.height).toFloat(),
                        maximumValue = 0f
                    )

                    controller.updateState(
                        bitmap = scaledImage,
                        screenWidth = screenWidth,
                        screenHeight = screenHeight,
                        offsetX = offsetX.absoluteValue,
                        offsetY = offsetY.absoluteValue
                    )
                }
            }
            .onGloballyPositioned { coordinates ->
                screenWidth = coordinates.size.width
                screenHeight = coordinates.size.height

                val scale = screenHeight / image.height.toFloat()

                scaledImage = image
                    .asAndroidBitmap()
                    .scale(
                        width = (image.width * scale).toInt(),
                        height = (image.height * scale).toInt()
                    )
                    .asImageBitmap()

                controller.updateState(
                    bitmap = scaledImage,
                    screenWidth = screenWidth,
                    screenHeight = screenHeight,
                    offsetX = offsetX.absoluteValue,
                    offsetY = offsetY.absoluteValue
                )
            }
    ) {
        drawImage(
            image = scaledImage,
            dstOffset = IntOffset(
                x = offsetX.toInt(),
                y = offsetY.toInt()
            ),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    IslamicQuotesTheme {
        CropImage(
            modifier = Modifier.fillMaxSize(),
            imageRes = R.drawable.img,
            controller = rememberCropController()
        )
    }
}