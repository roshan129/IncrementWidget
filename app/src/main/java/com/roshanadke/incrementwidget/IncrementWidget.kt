package com.roshanadke.incrementwidget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.text.Text

class IncrementWidget : GlanceAppWidget() {

    companion object {
        val countKey = intPreferencesKey("count")
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val count = currentState(countKey) ?: 0
            IncrementWidgetUI(count)
        }
    }
}

@Composable
fun IncrementWidgetUI(count: Int?) {

    Column(
        modifier = GlanceModifier.fillMaxSize()
            .background(Color.White),
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {
        Text(text = count.toString(),)
        Button(text = "Increment", onClick = actionRunCallback(IncrementActionCallback::class.java))
        Spacer(modifier = GlanceModifier.height(8.dp))
        Button(text = "Decrement", onClick = actionRunCallback(DecrementActionCallback::class.java))
    }
}


class IncrementActionCallback: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[IncrementWidget.countKey]
            if(currentCount != null) {
                prefs[IncrementWidget.countKey] = currentCount + 1
            } else {
                prefs[IncrementWidget.countKey] = 1
            }
        }
        IncrementWidget().update(context, glanceId)
    }
}

class DecrementActionCallback: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[IncrementWidget.countKey]
            if(currentCount != null) {
                prefs[IncrementWidget.countKey] = currentCount - 1
            } else {
                prefs[IncrementWidget.countKey] = -1
            }
        }
        IncrementWidget().update(context, glanceId)
    }

}

