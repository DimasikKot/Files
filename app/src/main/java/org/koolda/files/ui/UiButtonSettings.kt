package org.koolda.files.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koolda.files.KEY_SETTINGS
import org.koolda.files.MainVM
import org.koolda.files.screens.SettingsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun UiButtonSettings(
    modifier: Modifier = Modifier, mainVM: MainVM,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    with(sharedTransitionScope) {
        IconButton(
            onClick = { mainVM.navController.navigate(SettingsScreen) }, modifier = modifier
        ) {
            Icon(
                Icons.Default.Settings,
                null,
                tint = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier.sharedBounds(
                    rememberSharedContentState(KEY_SETTINGS), animatedVisibilityScope
                )
            )
        }
    }
}