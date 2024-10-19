package zelimkhan.magomadov.islamicquotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ripple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import zelimkhan.magomadov.islamicquotes.R
import zelimkhan.magomadov.islamicquotes.ui.background.BackgroundScreen
import zelimkhan.magomadov.islamicquotes.ui.core.LifecycleObserver
import zelimkhan.magomadov.islamicquotes.ui.theme.IslamicQuotesTheme
import zelimkhan.magomadov.islamicquotes.ui.wallpaper.WallpaperScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )

        setContent {
            IslamicQuotesTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()

                LifecycleObserver(
                    onStart = { viewModel.processIntent(MainIntent.NasheedResume) },
                    onStop = { viewModel.processIntent(MainIntent.NasheedPause) },
                )

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "quote"
                    ) {
                        composable("quote") {
                            WallpaperScreen(navController = navController)
                        }
                        composable("background") {
                            BackgroundScreen()
                        }
                    }

                    Image(
                        modifier = Modifier
                            .statusBarsPadding()
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(24.dp)
                            .clickable(
                                onClick = { viewModel.processIntent(MainIntent.ToggleNasheedMute) },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(
                                    bounded = false,
                                    radius = 17.dp,
                                )
                            ),
                        painter = if (state.isNasheedMuted) {
                            painterResource(id = R.drawable.ic_volume_off)
                        } else {
                            painterResource(id = R.drawable.ic_volume_on)
                        },
                        contentDescription = null
                    )
                }
            }
        }
    }
}

