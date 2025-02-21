package com.kevin.playwithcompose.appwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.kevin.playwithcompose.MainActivity
import com.kevin.playwithcompose.R

class PlayWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds.forEach { appWidgetId ->
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(
                    context,
                    MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val views:RemoteViews = RemoteViews(context.packageName, R.layout.layout_widget).apply{
                setOnClickPendingIntent(R.id.tv_widget,pendingIntent)
            }
            appWidgetManager.updateAppWidget(appWidgetId,views)

        }
    }
}