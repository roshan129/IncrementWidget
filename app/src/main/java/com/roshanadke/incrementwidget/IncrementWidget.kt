package com.roshanadke.incrementwidget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.text.Text

class IncrementWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {

            val repository = GlanceRepository(context)
            val count by repository.userPreferencesFlow.collectAsState(initial = 0)
            IncrementWidgetUI(
                count,
                incrementCount = {
                    repository.incrementCount()
                },
                decrementCount = {
                    repository.decrementCount()
                }
            )
        }
    }
}

@Composable
fun IncrementWidgetUI(
    count: Int?,
    incrementCount: () -> Unit,
    decrementCount: () -> Unit,
) {

    Column(
        modifier = GlanceModifier.fillMaxSize()
            .background(Color.White),
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {
        Text(text = count.toString())
        Button(text = "Increment", onClick = {
            incrementCount()
        })
        Spacer(modifier = GlanceModifier.height(8.dp))
        Button(text = "Decrement", onClick = {
            decrementCount()
        })
    }
}

