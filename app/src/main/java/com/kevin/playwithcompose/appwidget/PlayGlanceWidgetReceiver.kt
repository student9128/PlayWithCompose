package com.kevin.playwithcompose.appwidget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class PlayGlanceWidgetReceiver:GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = PlayGlanceWidget()
}