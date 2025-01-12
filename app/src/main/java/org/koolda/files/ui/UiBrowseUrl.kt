package org.koolda.files.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UiBrowseUrl(
    onClickView: () -> Unit,
    onClickDownload: () -> Unit,
    contentColor: Color = MaterialTheme.colorScheme.onSecondary,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {

        var textView by remember { mutableStateOf("View") }
        Card(
            modifier = Modifier.height(40.dp).clip(MaterialTheme.shapes.small).clickable {
                try {
                    onClickView()
                } catch (e: Exception) {
                    textView = "[ERROR] $e"
                }
            }
        ) {
            Box(Modifier.fillMaxHeight()) {
                Text(
                    textView,
                    modifier = Modifier.padding(5.dp).padding(horizontal = 5.dp)
                        .align(Alignment.Center),
                    color = contentColor,
                    maxLines = 1
                )
            }
        }

        var textDownload by remember { mutableStateOf("Download") }
        Card(
            modifier = Modifier.height(40.dp).padding(start = 5.dp).clip(MaterialTheme.shapes.small)
                .clickable {
                    try {
                        onClickDownload()
                    } catch (e: Exception) {
                        textDownload = "[ERROR] $e"
                    }
                }
        ) {
            Row(Modifier.padding(5.dp).fillMaxHeight()) {
                Image(
                    imageVector = Icons.Default.Download,
                    contentDescription = null,
                    modifier = Modifier.padding(3.dp).align(Alignment.CenterVertically)
                )
                Text(
                    textDownload,
                    modifier = Modifier.padding(start = 2.dp, end = 5.dp)
                        .align(Alignment.CenterVertically),
                    color = contentColor,
                    maxLines = 1
                )
            }
        }

    }
}