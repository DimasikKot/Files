package org.koolda.files.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import org.koolda.files.KEY_SETTINGS
import org.koolda.files.MainVM
import kotlin.math.roundToInt

@Serializable
object SettingsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SettingsScreen(
    mainVM: MainVM,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize()) {
        with(sharedTransitionScope) {
            Card(
                modifier = Modifier
                    .size(400.dp, 700.dp)
                    .align(Alignment.Center)
                    .sharedBounds(
                        rememberSharedContentState(KEY_SETTINGS),
                        animatedVisibilityScope,
                        renderInOverlayDuringTransition = false
                    ), elevation = CardDefaults.cardElevation(5.dp)
            ) {
                LazyColumn(
                    Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    item {
                        Text("Debug")
                        Switch(mainVM.debugIsView, { mainVM.debugIsView = !mainVM.debugIsView })
                        if (mainVM.debugIsView) {
                            Text("Debug transparent: ${(mainVM.debugTransparent * 100).roundToInt()}%")
                            Slider(
                                mainVM.debugTransparent, { mainVM.debugTransparent = it }, steps = 9
                            )
                        }
                    }
                    item {
                        var sliderRange by remember { mutableStateOf((mainVM.serversIpStart)..(mainVM.serversIpEnd)) }
                        val startValue = sliderRange.start.roundToInt()
                        val endValue = sliderRange.endInclusive.roundToInt()
                        Text("Servers Ip Range: $startValue..$endValue")
                        RangeSlider(
                            value = sliderRange,
                            onValueChange = { newRange ->
                                sliderRange = newRange
                                mainVM.serversIpStart = newRange.start
                                mainVM.serversIpEnd = newRange.endInclusive
                            },
                            valueRange = 0f..255f,
                            steps = 254, // Количество шагов (255 - 1)
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
            }
        }
    }
}