package org.koolda.files.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import org.koolda.files.MainVM
import org.koolda.files.ui.UiButtonSettings

@Serializable
object ServersScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ServersScreen(
    mainVM: MainVM,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.Start
    ) {
        Card(
            shape = MaterialTheme.shapes.extraLarge, elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Row(
                Modifier
                    .padding(5.dp)
                    .height(60.dp)
            ) {
                var stateTextFielsIP by remember {
                    mutableStateOf(mainVM.serverIP)
                }
                IconButton(
                    onClick = { mainVM.serverIP = stateTextFielsIP },
                    enabled = mainVM.connectStatus != "true"
                ) {
                    Icon(
                        Icons.Default.Save, null, tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
                TextField(
                    stateTextFielsIP,
                    { stateTextFielsIP = it },
                    enabled = mainVM.connectStatus != "true",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(
                            Alignment.CenterVertically
                        )
                        .weight(1f),
                    singleLine = true
                )
                UiButtonSettings(
                    mainVM = mainVM,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }
    }
}