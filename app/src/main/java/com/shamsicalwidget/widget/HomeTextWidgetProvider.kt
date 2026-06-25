package com.shamsicalwidget.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.shamsicalwidget.R
import com.shamsicalwidget.util.JalaliCalendar

class HomeTextWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach { updateWidget(context, appWidgetManager, it) }
    }
    
    override fun onEnabled(context: Context) {
    super.onEnabled(context)
    WidgetUpdateService.scheduleDailyAlarm(context)
    }

    override fun onDisabled(context: Context) {
    super.onDisabled(context)
    WidgetUpdateService.cancelAlarm(context)
    }

    companion object {
        fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val jalali = JalaliCalendar.today()
            val views = RemoteViews(context.packageName, R.layout.widget_home_text)

            val text = "${JalaliCalendar.dayName(jalali.dayOfWeek)} ${JalaliCalendar.toPersianDigits(jalali.day)} ${JalaliCalendar.monthName(jalali.month)}"
            views.setTextViewText(R.id.tv_home_text, text)

            val calIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("content://com.android.calendar/time/")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            }
            val pending = PendingIntent.getActivity(
                context, 1, calIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_home_text_root, pending)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
