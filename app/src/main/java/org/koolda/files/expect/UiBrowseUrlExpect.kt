package org.koolda.files.expect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import org.koolda.files.MainVM
import org.koolda.files.ui.UiBrowseUrl

@Composable
fun UiBrowseUrlExpect(
    mainVM: MainVM,
    modifier: Modifier,
) {

//    val content = LocalContext.current
    val uriHandler = LocalUriHandler.current

    val url by remember {
        mutableStateOf(
            "http://${mainVM.serverIP}/${mainVM.diskForRequest}/${mainVM.pathForRequest}:${mainVM.currentFile}"
        )
    }

    UiBrowseUrl(modifier = modifier, onClickView = {
//        CustomTabsIntent.Builder().build().launchUrl(content, Uri.parse(url))
        uriHandler.openUri(url)
    }, onClickDownload = {
//        CustomTabsIntent.Builder().build().launchUrl(content, Uri.parse("$url/d"))
        uriHandler.openUri("$url/d")
    })

}