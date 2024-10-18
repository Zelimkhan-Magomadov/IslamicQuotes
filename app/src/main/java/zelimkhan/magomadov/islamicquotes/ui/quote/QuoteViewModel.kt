package zelimkhan.magomadov.islamicquotes.ui.quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zelimkhan.magomadov.islamicquotes.data.media.MediaPlayerManager
import zelimkhan.magomadov.islamicquotes.domain.wallpaper.GenerateWallpaperUseCase
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val generateWallpaperUseCase: GenerateWallpaperUseCase,
    private val mediaPlayerManager: MediaPlayerManager
) : ViewModel() {
    private val _quoteScreenState = MutableStateFlow(QuoteScreenState())
    val quoteScreenState = _quoteScreenState.asStateFlow()

    init {
        processIntent(QuoteIntent.ChangeQuote)
        mediaPlayerManager.initMediaPlayer()
    }

    fun processIntent(intent: QuoteIntent) {
        when (intent) {
            QuoteIntent.ChangeQuote -> {
                viewModelScope.launch {
                    _quoteScreenState.value = quoteScreenState.value.copy(
                        quoteImage = generateWallpaperUseCase()
                    )
                }
            }

            QuoteIntent.ToggleNasheedMute -> {
                mediaPlayerManager.toggleMute()
                _quoteScreenState.value = quoteScreenState.value.copy(
                    isNasheedMuted = mediaPlayerManager.isMuted
                )
            }

            QuoteIntent.NasheedPause -> mediaPlayerManager.pause()
            QuoteIntent.NasheedResume -> {
                if (mediaPlayerManager.isMuted.not()) {
                    mediaPlayerManager.resume()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerManager.release()
    }
}