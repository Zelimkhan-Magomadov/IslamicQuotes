package zelimkhan.magomadov.islamicquotes.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import zelimkhan.magomadov.islamicquotes.domain.wallpaper.SetWallpaperUseCase
import javax.inject.Inject

@AndroidEntryPoint
class WallpaperUpdateReceiver : BroadcastReceiver() {
    @Inject
    lateinit var setWallpaperUseCase: SetWallpaperUseCase

    override fun onReceive(context: Context, intent: Intent) {
        CoroutineScope(Dispatchers.IO).launch {
            setWallpaperUseCase()
        }
    }
}