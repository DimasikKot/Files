package org.koolda.files.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import org.koolda.files.MainVM
import org.koolda.files.expect.UiBrowseUrlExpect
import org.koolda.files.ui.UiButtonSettings
import org.koolda.files.ui.UiCatalogItem
import org.koolda.files.ui.UiNote

@Serializable
object FoldersScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FoldersScreen(
    mainVM: MainVM,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(10.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.Start
    ) {
        Card(
            shape = MaterialTheme.shapes.extraLarge, elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Row(
                Modifier
                    .padding(5.dp)
                    .height(48.dp)
            ) {
                IconButton(onClick = { mainVM.pathForRequest = ":" }) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        null,
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
                Text(
                    "${mainVM.diskForRequest}${if (mainVM.diskForRequest != "") "/" else ""}${
                        mainVM.pathForRequest.replace(
                            ":", "/"
                        )
                    }${if (mainVM.currentFile != "") "/" else ""}${mainVM.currentFile}",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(
                            Alignment.CenterVertically
                        )
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                )
                UiButtonSettings(
                    mainVM = mainVM,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .animateContentSize(),
            contentPadding = PaddingValues(top = 5.dp)
        ) {
            items(mainVM.listCatalogItems) {
                Row {
                    AnimatedVisibility(visible = it.name == mainVM.currentFile) {
                        UiBrowseUrlExpect(
                            mainVM = mainVM,
                            Modifier
                                .height(40.dp)
                                .padding(end = 5.dp)
                        )
                    }
                    UiCatalogItem(it = it,
                        modifier = Modifier
                            .height(40.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .clickable {
                                if (it.isDir) {
                                    if (mainVM.diskForRequest.isEmpty()) {
                                        mainVM.diskForRequest = it.name
                                    } else {
                                        mainVM.pathForRequest = it.name
                                    }
                                } else {
                                    if (it.extension != null) {
                                        mainVM.currentFile = if (mainVM.currentFile != it.name) {
                                            it.name
                                        } else {
                                            ""
                                        }
                                    }
                                }
                            })
                }
            }
            items(mainVM.listNotes) {
                UiNote(it = it,
                    modifier = Modifier
                        .height(40.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .clickable {
                            mainVM.diskForRequest = it.link.disk
                            mainVM.pathForRequest = it.link.path
                            mainVM.currentFile = it.link.file
                        })
            }
        }
    }
}