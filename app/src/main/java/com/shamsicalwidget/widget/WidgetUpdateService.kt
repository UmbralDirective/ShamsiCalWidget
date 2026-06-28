package com.shamsicalwidget.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import java.util.Calendar

class WidgetUpdateService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        updateAllWidgets(context)
        scheduleDailyAlarm(context)
    }

    companion object {
        private const val ACTION_UPDATE = "com.shamsicalwidget.ACTION_UPDATE_WIDGETS"
        private const val REQUEST_CODE = 100

        fun updateAllWidgets(context: Context) {
            val mgr = AppWidgetManager.getInstance(context)

            mgr.getAppWidgetIds(ComponentName(context, HomeWidgetProvider::class.java))
                .forEach { HomeWidgetProvider.updateWidget(context, mgr, it) }

            mgr.getAppWidgetIds(ComponentName(context, HomeTextWidgetProvider::class.java))
                .forEach { HomeTextWidgetProvider.updateWidget(context, mgr, it) }

            mgr.getAppWidgetIds(ComponentName(context, LockScreenWidgetProvider::class.java))
                .forEach { LockScreenWidgetProvider.updateWidget(context, mgr, it) }
        }

        fun scheduleDailyAlarm(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // explicit intent - ضروری برای Android 8+
            val intent = Intent(context, WidgetUpdateService::class.java).apply {
                action = ACTION_UPDATE
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // فردا نیمه‌شب
            val midnight = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 5)
                set(Calendar.MILLISECOND, 0)
            }

            // RTC_WAKEUP - گوشی رو از خواب بیدار می‌کنه
            try {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    midnight.timeInMillis,
                    pendingIntent
                )
            } catch (e: SecurityException) {
                // fallback برای Android 12+ که exact alarm نیاز به permission داره
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    midnight.timeInMillis,
                    pendingIntent
                )
            }
        }

        fun cancelAlarm(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, WidgetUpdateService::class.java).apply {
                action = ACTION_UPDATE
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context, REQUEST_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }
}
