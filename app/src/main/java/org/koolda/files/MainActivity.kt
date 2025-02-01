package org.koolda.files

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koolda.files.expect.Database
import org.koolda.files.screens.FoldersScreen
import org.koolda.files.screens.ServersScreen
import org.koolda.files.screens.SettingsScreen
import org.koolda.files.ui.UiDebug
import org.koolda.files.ui.theme.FilesTheme
import kotlin.system.exitProcess

const val KEY_SETTINGS = "KEY_SETTINGS"

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val mainVM = remember { MainVM(Database(this), navController) }
            SharedTransitionLayout {
                FilesTheme {
                    Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = if (mainVM.connectStatus == "true") FoldersScreen else ServersScreen,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<FoldersScreen> {
                                BackHandler(mainVM.diskForRequest != "") {
                                    mainVM.pathForRequest = ":"
                                }
                                BackHandler(mainVM.diskForRequest == "") {
                                    finishAndRemoveTask()
                                    exitProcess(0)
                                }
                                FoldersScreen(
                                    mainVM = mainVM,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@composable
                                )
                            }
                            composable<ServersScreen> {
                                if (mainVM.connectStatus != "true") {
                                    BackHandler {
                                        finishAndRemoveTask()
                                        exitProcess(0)
                                    }
                                }
                                ServersScreen(
                                    mainVM = mainVM,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@composable
                                )
                            }
                            composable<SettingsScreen> {
                                SettingsScreen(
                                    mainVM = mainVM,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@composable
                                )
                            }
                        }
                        if (mainVM.debugIsView) {
                            UiDebug(mainVM)
                        }
                    }
                }
            }
        }
    }
}