package zelimkhan.magomadov.islamicquotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import zelimkhan.magomadov.islamicquotes.domain.scheduler.SetScheduledWallpaperUpdateUseCase
import zelimkhan.magomadov.islamicquotes.domain.wallpaper.SetWallpaperUseCase
import zelimkhan.magomadov.islamicquotes.ui.quote.QuoteScreen
import zelimkhan.magomadov.islamicquotes.ui.theme.IslamicQuotesTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var setScheduledWallpaperUpdateUseCase: SetScheduledWallpaperUpdateUseCase

    @Inject
    lateinit var setWallpaperUseCase: SetWallpaperUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )

        setScheduledWallpaperUpdateUseCase()

        lifecycleScope.launch {
            setWallpaperUseCase()
        }

        setContent {
            IslamicQuotesTheme {
                QuoteScreen()
            }
        }
    }
}