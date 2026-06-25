package com.shamsicalwidget.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val appWidgetManager = AppWidgetManager.getInstance(context)

        // Re-render all widgets immediately after boot
        val homeComponent = ComponentName(context, HomeWidgetProvider::class.java)
        appWidgetManager.getAppWidgetIds(homeComponent).forEach { id ->
            HomeWidgetProvider.updateWidget(context, appWidgetManager, id)
        }
        val lockComponent = ComponentName(context, LockScreenWidgetProvider::class.java)
        appWidgetManager.getAppWidgetIds(lockComponent).forEach { id ->
            LockScreenWidgetProvider.updateWidget(context, appWidgetManager, id)
        }

        // Reschedule midnight alarm
        WidgetUpdateService.scheduleDailyAlarm(context)
    }
}
