package zelimkhan.magomadov.islamicquotes.ui.core

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.WindowManager

fun Context.getScreenSize(): Size {
    val screenWidth: Int
    val screenHeight: Int

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = windowManager.currentWindowMetrics
        screenWidth = metrics.bounds.width()
        screenHeight = metrics.bounds.height()
    } else {
        val display =
            (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val realMetrics = DisplayMetrics()
        display.getRealMetrics(realMetrics)
        screenWidth = realMetrics.widthPixels
        screenHeight = realMetrics.heightPixels
    }

    return Size(screenWidth, screenHeight)
}