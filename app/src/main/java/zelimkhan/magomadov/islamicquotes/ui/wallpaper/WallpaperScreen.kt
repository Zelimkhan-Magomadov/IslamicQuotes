package zelimkhan.magomadov.islamicquotes.ui.wallpaper

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import zelimkhan.magomadov.islamicquotes.ui.theme.IslamicQuotesTheme

@Composable
fun WallpaperScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: WallpaperViewModel = hiltViewModel()
    val quoteScreenState by viewModel.state.collectAsStateWithLifecycle()

    WallpaperScreenContent(
        modifier = modifier,
        state = quoteScreenState,
        onIntent = viewModel::processIntent,
        navigateToBackGroundScreen = { navController.navigate("background") }
    )
}

@Composable
fun WallpaperScreenContent(
    modifier: Modifier = Modifier,
    state: WallpaperState = WallpaperState(),
    onIntent: (WallpaperIntent) -> Unit = {},
    navigateToBackGroundScreen: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = state.wallpaper.asImageBitmap(),
            contentDescription = null
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onIntent(WallpaperIntent.ChangeQuote) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF737272)),
                ) {
                    Text(text = "Сменить цитату")
                }

                Button(
                    onClick = { onIntent(WallpaperIntent.SetWallpaper) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF737272)),
                ) {
                    Text(text = "Установить обои")
                }

                Button(
                    onClick = {
                        navigateToBackGroundScreen()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF737272)),
                ) {
                    Text(text = "Сменить фон")
                }
            }
        }

    }
}

@Preview
@Composable
private fun Preview() {
    IslamicQuotesTheme {
        Surface {
            WallpaperScreenContent()
        }
    }
}