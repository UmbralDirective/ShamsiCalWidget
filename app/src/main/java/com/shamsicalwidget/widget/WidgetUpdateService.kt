package com.shamsicalwidget.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import java.util.Calendar

/**
 * Handles daily midnight updates for all widgets.
 * Uses AlarmManager to fire at midnight (00:00:05) each day.
 */
class WidgetUpdateService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val appWidgetManager = AppWidgetManager.getInstance(context)

        // Update Home widgets (2x2)
        val homeComponent = ComponentName(context, HomeWidgetProvider::class.java)
        appWidgetManager.getAppWidgetIds(homeComponent).forEach { id ->
            HomeWidgetProvider.updateWidget(context, appWidgetManager, id)
        }

        // Update Home Text widgets (متنی)
        val homeTextComponent = ComponentName(context, HomeTextWidgetProvider::class.java)
        appWidgetManager.getAppWidgetIds(homeTextComponent).forEach { id ->
            HomeTextWidgetProvider.updateWidget(context, appWidgetManager, id)
        }

        // Update Lock Screen widgets
        val lockComponent = ComponentName(context, LockScreenWidgetProvider::class.java)
        appWidgetManager.getAppWidgetIds(lockComponent).forEach { id ->
            LockScreenWidgetProvider.updateWidget(context, appWidgetManager, id)
        }

        // Schedule next day's alarm
        scheduleDailyAlarm(context)
    }

    companion object {
        private const val ACTION_UPDATE = "com.shamsicalwidget.ACTION_UPDATE_WIDGETS"

        fun scheduleDailyAlarm(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, WidgetUpdateService::class.java).apply {
                action = ACTION_UPDATE
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, 100, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Next midnight + 5 seconds
            val midnight = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 5)
                set(Calendar.MILLISECOND, 0)
            }

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC,
                midnight.timeInMillis,
                pendingIntent
            )
        }

        fun cancelAlarm(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, WidgetUpdateService::class.java).apply {
                action = ACTION_UPDATE
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, 100, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }
}
