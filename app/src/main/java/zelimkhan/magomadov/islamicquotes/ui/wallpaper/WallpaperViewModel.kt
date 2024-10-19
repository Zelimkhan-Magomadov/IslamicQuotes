package zelimkhan.magomadov.islamicquotes.ui.wallpaper

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
class WallpaperViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository,
    private val backgroundRepository: BackgroundRepository,
    private val wallpaperGenerator: WallpaperGenerator,
    private val wallpaperService: WallpaperService
) : ViewModel() {
    private val _state = MutableStateFlow(WallpaperState())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WallpaperState()
    )

    init {
        viewModelScope.launch {
            combine(
                quoteRepository.currentQuoteStream,
                backgroundRepository.getBackgroundStream()
            ) { quote, background -> wallpaperGenerator(quote, background) }.collect { wallpaper ->
                _state.update { state -> state.copy(wallpaper = wallpaper) }
            }
        }
        processIntent(WallpaperIntent.ChangeQuote)
    }

    fun processIntent(intent: WallpaperIntent) {
        when (intent) {
            WallpaperIntent.ChangeQuote -> {
                viewModelScope.launch {
                    quoteRepository.changeQuote()
                }
            }

            WallpaperIntent.SetWallpaper -> {
                wallpaperService.setLockScreenWallpaper(state.value.wallpaper)
            }
        }
    }
}