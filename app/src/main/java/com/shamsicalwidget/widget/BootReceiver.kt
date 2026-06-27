package com.shamsicalwidget.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(

        context: Context,

        intent: Intent

    ) {

        val manager =

            AppWidgetManager.getInstance(

                context

            )

        manager.getAppWidgetIds(

            ComponentName(

                context,

                HomeWidgetProvider::class.java

            )

        ).forEach {

            HomeWidgetProvider.updateWidget(

                context,

                manager,

                it

            )

        }

        manager.getAppWidgetIds(

            ComponentName(

                context,

                HomeTextWidgetProvider::class.java

            )

        ).forEach {

            HomeTextWidgetProvider.updateWidget(

                context,

                manager,

                it

            )

        }

        manager.getAppWidgetIds(

            ComponentName(

                context,

                LockScreenWidgetProvider::class.java

            )

        ).forEach {

            LockScreenWidgetProvider.updateWidget(

                context,

                manager,

                it

            )

        }

        WidgetUpdateService.scheduleDailyAlarm(

            context

        )

    }

}
