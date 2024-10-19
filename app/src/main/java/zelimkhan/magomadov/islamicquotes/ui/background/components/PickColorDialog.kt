package zelimkhan.magomadov.islamicquotes.ui.background.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.toColorInt
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import zelimkhan.magomadov.islamicquotes.ui.theme.IslamicQuotesTheme

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun PickColorDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    initialColor: Color = Color.White,
    onColorSelected: (Color) -> Unit = {}
) {
    val controller = remember {
        ColorPickerController().apply {
            debounceDuration = 200L
        }
    }

    var hexColor by remember {
        val colorInt = initialColor.value.toInt()
        val colorHex = colorInt.toHexString()
        mutableStateOf(colorHex)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(),
    ) {
        Column(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(
                    vertical = 32.dp,
                    horizontal = 50.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HsvColorPicker(
                modifier = Modifier.size(250.dp),
                controller = controller,
                initialColor = initialColor,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    if (colorEnvelope.fromUser) {
                        hexColor = colorEnvelope.hexCode
                    }
                }
            )

            Spacer(Modifier.height(32.dp))

            AlphaSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                initialColor = initialColor,
                borderSize = 0.dp,
                controller = controller,
            )

            Spacer(Modifier.height(16.dp))

            BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp),
                initialColor = initialColor,
                borderSize = 0.dp,
                controller = controller,
            )

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HexColorTextField(
                    value = hexColor,
                    onCompleted = {
                        controller.selectByColor(
                            color = Color(("#$it").toColorInt()),
                            fromUser = false
                        )
                    }
                )

                AlphaTile(
                    modifier = Modifier
                        .size(40.dp)
                        .aspectRatio(1 / 1f)
                        .clip(CircleShape),
                    controller = controller
                )
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    val selectedColor = controller.selectedColor.value
                    onColorSelected(selectedColor)
                    onDismiss()
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Выбрать цвет")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    IslamicQuotesTheme {
        Surface {
            PickColorDialog()
        }
    }
}