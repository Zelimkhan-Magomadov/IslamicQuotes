package zelimkhan.magomadov.islamicquotes.ui.background.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zelimkhan.magomadov.islamicquotes.ui.theme.IslamicQuotesTheme

@Composable
fun HexColorTextField(
    modifier: Modifier = Modifier,
    value: String,
    onCompleted: (String) -> Unit
) {
    var hexColor by remember(value) {
        mutableStateOf(TextFieldValue(value, TextRange(value.length)))
    }

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        BasicTextField(
            value = hexColor,
            onValueChange = {
                if (it.text.isHexColor()) {
                    hexColor = it
                }
            },
            textStyle = TextStyle(
                fontWeight = FontWeight.Medium,
                color = Color.White
            ),
            cursorBrush = SolidColor(Color.LightGray),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "#", color = Color.White)
                    innerTextField()
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    val completed = hexColor.text.completeHexColor()
                    hexColor = TextFieldValue(
                        text = completed,
                        selection = TextRange(completed.length)
                    )
                    onCompleted(hexColor.text)
                }
            )
        )
    }
}

@OptIn(ExperimentalStdlibApi::class)
private fun String.isHexColor(): Boolean {
    return try {
        this.hexToInt()
        true
    } catch (e: IllegalArgumentException) {
        isEmpty()
    }
}

private fun String.completeHexColor(): String {
    return this.padEnd(8, '0')
}


@Preview
@Composable
private fun Preview() {
    IslamicQuotesTheme {
        Surface(
            color = Color.DarkGray,
        ) {
            HexColorTextField(
                value = "",
                onCompleted = {}
            )
        }
    }
}