package zelimkhan.magomadov.islamicquotes.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import zelimkhan.magomadov.islamicquotes.data.media.MediaPlayerManager
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mediaPlayerManager: MediaPlayerManager,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        mediaPlayerManager.initMediaPlayer()
    }

    fun processIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.NasheedPause -> {
                mediaPlayerManager.pause()
            }

            MainIntent.NasheedResume -> {
                if (mediaPlayerManager.isMuted.not()) {
                    mediaPlayerManager.resume()
                }
            }

            MainIntent.ToggleNasheedMute -> {
                mediaPlayerManager.toggleMute()
                _state.update { state ->
                    state.copy(isNasheedMuted = mediaPlayerManager.isMuted)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerManager.release()
    }
}