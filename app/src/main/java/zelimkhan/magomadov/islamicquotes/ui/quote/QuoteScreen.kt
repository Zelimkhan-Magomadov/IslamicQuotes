package zelimkhan.magomadov.islamicquotes.ui.quote

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import zelimkhan.magomadov.islamicquotes.R
import zelimkhan.magomadov.islamicquotes.ui.core.LifecycleObserver

@Composable
fun QuoteScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: QuoteViewModel = viewModel()
    val quoteScreenState by viewModel.quoteScreenState.collectAsStateWithLifecycle()

    QuoteScreenContent(
        modifier = modifier,
        state = quoteScreenState,
        onIntent = viewModel::processIntent
    )
}

@Composable
fun QuoteScreenContent(
    modifier: Modifier = Modifier,
    state: QuoteScreenState = QuoteScreenState(),
    onIntent: (QuoteIntent) -> Unit
) {
    LifecycleObserver(
        onStart = { onIntent(QuoteIntent.NasheedResume) },
        onPause = { onIntent(QuoteIntent.NasheedPause) },
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeContentPadding()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = state.quoteImage.asImageBitmap(),
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable(onClick = { onIntent(QuoteIntent.ToggleNasheedMute) })
                .size(24.dp),
            painter = if (state.isNasheedMuted) {
                painterResource(id = R.drawable.ic_volume_off)
            } else {
                painterResource(id = R.drawable.ic_volume_on)
            },
            contentDescription = null
        )

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            onClick = { onIntent(QuoteIntent.ChangeQuote) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF737272)),
        ) {
            Text(text = "Сменить")
        }
    }
}