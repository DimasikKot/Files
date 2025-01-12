package org.koolda.files.ui

import androidx.collection.size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import org.koolda.files.MainVM

@Composable
fun UiDebug(mainVM: MainVM) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.align(Alignment.BottomEnd).size(200.dp, 300.dp)
                .alpha(mainVM.debugTransparent).background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            item {
                Text(mainVM.connectStatus)
            }
            item {
                Text(mainVM.diskForRequest)
            }
            item {
                Text(mainVM.pathForRequest)
            }
            item {
                Text(mainVM.currentFile)
            }
            item {
                Text(mainVM.serverIP)
            }
            item {
                Text(mainVM.navController.graph.nodes.size.toString())
            }
            item {
                Text(mainVM.navController.graph.nodes.size().toString())
            }
            item {
                Text(mainVM.navController.graph.toString())
            }
            item {
                Text(mainVM.listCatalogItems.toString())
            }
        }
    }
}