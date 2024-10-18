package zelimkhan.magomadov.islamicquotes.data.media

import android.content.Context
import android.media.MediaPlayer
import zelimkhan.magomadov.islamicquotes.R

class MediaPlayerManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var currentPosition = 0
    var isMuted = false
        private set

    fun initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(context, R.raw.ya_fauzaman).apply {
            isLooping = true
            start()
        }
    }

    fun toggleMute() {
        isMuted = !isMuted
        when (isMuted) {
            true -> pause()
            false -> resume()
        }
    }

    fun pause() {
        currentPosition = mediaPlayer?.currentPosition ?: 0
        mediaPlayer?.pause()
    }

    fun resume() {
        mediaPlayer?.seekTo(currentPosition)
        mediaPlayer?.start()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        currentPosition = 0
    }
}