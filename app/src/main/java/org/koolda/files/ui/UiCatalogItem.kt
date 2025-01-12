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
import org.koolda.files.serializable.CatalogItem

@Composable
fun UiCatalogItem(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onSecondary,
    it: CatalogItem,
) {
    Card(modifier = modifier) {
        Row(Modifier.padding(5.dp)) {
            Image(
                if (it.isDir) {
                    if (it.extension == null) {
                        painterResource(R.drawable.folder_null)
                    } else {
                        painterResource(R.drawable.back)
                    }
                } else {
                    when (it.extension) {
                        "ai" -> painterResource(R.drawable.ai)
                        "apk" -> painterResource(R.drawable.apk)
                        "avi" -> painterResource(R.drawable.avi)
                        "css" -> painterResource(R.drawable.css)
                        "dbf" -> painterResource(R.drawable.dbf)
                        "doc" -> painterResource(R.drawable.doc)
                        "docx" -> painterResource(R.drawable.docx)
                        "dwg" -> painterResource(R.drawable.dwg)
                        "exe" -> painterResource(R.drawable.exe)
                        "fla" -> painterResource(R.drawable.fla)
                        "gif" -> painterResource(R.drawable.gif)
                        "html" -> painterResource(R.drawable.html)
                        "ico" -> painterResource(R.drawable.ico)
                        "jpg" -> painterResource(R.drawable.jpg)
                        "js" -> painterResource(R.drawable.js)
                        "json" -> painterResource(R.drawable.json)
                        "mp3" -> painterResource(R.drawable.mp3)
                        "mp4" -> painterResource(R.drawable.mp4)
                        "pdf" -> painterResource(R.drawable.pdf)
                        "png" -> painterResource(R.drawable.png)
                        "ppt" -> painterResource(R.drawable.ppt)
                        "pptx" -> painterResource(R.drawable.pptx)
                        "psd" -> painterResource(R.drawable.psd)
                        "rtf" -> painterResource(R.drawable.rtf)
                        "svg" -> painterResource(R.drawable.svg)
                        "txt" -> painterResource(R.drawable.txt)
                        "xls" -> painterResource(R.drawable.xls)
                        "xlsx" -> painterResource(R.drawable.xlsx)
                        "xml" -> painterResource(R.drawable.xml)
                        "zip", "7z", "rar" -> painterResource(R.drawable.zip)
                        else -> painterResource(R.drawable.back)
                    }
                },
                contentDescription = null,
                modifier = Modifier.padding(3.dp).align(Alignment.CenterVertically)
            )
            Text(
                it.name,
                color = contentColor,
                modifier = Modifier.padding(start = 2.dp, end = 5.dp)
                    .align(Alignment.CenterVertically),
                maxLines = 1
            )
        }
    }
}