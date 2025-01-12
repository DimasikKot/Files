package org.koolda.files.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.koolda.files.R
import org.koolda.files.serializable.Note

@Composable
fun UiNote(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.secondary,
    it: Note,
) {
    Card(modifier = modifier) {
        Row(Modifier.padding(5.dp)) {
            Image(
                painterResource(R.drawable.folder_big),
                contentDescription = null,
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                it.link.disk + "/" + it.link.path.replace(":", "/"),
                color = contentColor,
                modifier = Modifier
                    .padding(start = 2.dp, end = 5.dp)
                    .align(Alignment.CenterVertically),
                maxLines = 1
            )
        }
    }
}