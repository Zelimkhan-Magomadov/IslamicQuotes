package zelimkhan.magomadov.islamicquotes.data.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import zelimkhan.magomadov.islamicquotes.domain.scheduler.Scheduler
import kotlin.reflect.KClass

class SchedulerImpl(
    private val context: Context,
    private val broadcastReceiverClass: KClass<out BroadcastReceiver>
) : Scheduler {
    override fun setRepeatingExecution(interval: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, broadcastReceiverClass.java)

        val existingPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )

        if (existingPendingIntent != null) {
            return
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerAtMillis = SystemClock.elapsedRealtime() + interval

        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerAtMillis,
            interval,
            pendingIntent
        )
    }
}