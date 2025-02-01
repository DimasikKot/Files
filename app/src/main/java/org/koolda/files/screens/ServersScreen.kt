package org.koolda.files.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import org.koolda.files.MainVM
import org.koolda.files.ui.UiButtonSettings
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt

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
                    onClick = { mainVM.serverIP = stateTextFielsIP }
                ) {
                    Icon(
                        Icons.Default.Save, null, tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
                TextField(
                    stateTextFielsIP,
                    { stateTextFielsIP = it },
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
        DeviceScannerApp(mainVM)
    }
}

@Composable
fun DeviceScannerApp(mainVM: MainVM) {
    val currentIP = remember { mutableStateOf("") }
    val servers = remember { mutableStateOf<MutableList<String>>(mutableListOf()) }

    LaunchedEffect(Unit) {
        scanDevices(currentIP, servers, mainVM.serversIpStart.roundToInt(), mainVM.serversIpEnd.roundToInt())
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .animateContentSize(),
        contentPadding = PaddingValues(top = 5.dp)
    ) {
        item {
            Text("Devices using Ktor server: ${currentIP.value}")
        }
        items(servers.value) {
            Row {
                Card {
                    Text(it)
                }
                Text(
                    "true",
                    modifier = Modifier
                        .height(40.dp)
                        .padding(end = 5.dp)
                )
            }
        }
    }
}

private suspend fun scanDevices(
    currentIP: MutableState<String>,
    servers: MutableState<MutableList<String>>,
    start: Int,
    end: Int
) =
    withContext(Dispatchers.IO) {
        val serversIO = mutableListOf<String>()
        val baseIp = "192.168"

        for (j in start..end) {
            for (i in 1..255) {
                val ip = "$baseIp.$j.$i"
                currentIP.value = ip
                if (isDeviceUsingKtor(ip)) {
                    serversIO.add(ip)
                }
            }
            servers.value = serversIO
        }
    }

private suspend fun isDeviceUsingKtor(
    ip: String
): Boolean = withContext(Dispatchers.IO) {
    val url = URL("http://$ip:8888/connect")
    (url.openConnection() as? HttpURLConnection)?.run {
        requestMethod = "GET"
        connectTimeout = 20
        readTimeout = 1000
        try {
            val responseCode = responseCode
            if (responseCode == 200) {
                return@withContext inputStream.bufferedReader().use { it.readLine() == "true" }
            }
        } catch (e: Exception) {
            // Ignore exceptions (device not reachable, etc.)
        } finally {
            disconnect()
        }
    }
    false
}