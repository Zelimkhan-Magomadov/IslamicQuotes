package zelimkhan.magomadov.islamicquotes.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import zelimkhan.magomadov.islamicquotes.domain.scheduler.SetScheduledWallpaperUpdateUseCase
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var setScheduledWallpaperUpdateUseCase: SetScheduledWallpaperUpdateUseCase

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            setScheduledWallpaperUpdateUseCase()
        }
    }
}