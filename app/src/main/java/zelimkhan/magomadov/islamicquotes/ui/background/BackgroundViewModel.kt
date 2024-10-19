package zelimkhan.magomadov.islamicquotes.ui.background

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zelimkhan.magomadov.islamicquotes.data.background.BackgroundRepository
import zelimkhan.magomadov.islamicquotes.data.quote.QuoteRepository
import zelimkhan.magomadov.islamicquotes.data.wallpaper.WallpaperGenerator
import zelimkhan.magomadov.islamicquotes.data.wallpaper.WallpaperService
import javax.inject.Inject

@HiltViewModel
class BackgroundViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository,
    private val backgroundRepository: BackgroundRepository,
    private val wallpaperGenerator: WallpaperGenerator,
    private val wallpaperService: WallpaperService,
) : ViewModel() {
    private val _state = MutableStateFlow(BackgroundState())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BackgroundState()
    )

    init {
        viewModelScope.launch {
            combine(
                quoteRepository.currentQuoteStream,
                backgroundRepository.getBackgroundStream()
            ) { quote, background -> wallpaperGenerator(quote, background) }.collect {
                _state.update { state -> state.copy(quoteBackground = it) }
            }
        }
    }

    fun processIntent(intent: BackgroundIntent) {
        when (intent) {
            is BackgroundIntent.SetColorBackground -> {
                viewModelScope.launch {
                    backgroundRepository.saveColorBackground(intent.color)
                }
            }

            is BackgroundIntent.SetImageBackground -> {
                viewModelScope.launch {
                    backgroundRepository.saveImageBackground(intent.bitmap)
                }
            }

            is BackgroundIntent.LoadImage -> {
                _state.value = _state.value.copy(quoteBackground = intent.bitmap)
            }

            BackgroundIntent.LoadLockScreenWallpaper -> {
                viewModelScope.launch {
                    try {
                        val currentWallpaper = wallpaperService.getCurrentLockScreenWallpaper()
                        backgroundRepository.saveImageBackground(currentWallpaper)
                    } catch (e: Exception) {
                        // TODO
                    }
                }
            }
        }
    }
}