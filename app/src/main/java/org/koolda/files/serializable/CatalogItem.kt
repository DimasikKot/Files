package org.koolda.files.serializable

import kotlinx.serialization.Serializable

@Serializable
data class CatalogItem(
    val isDir: Boolean,
    val name: String,
    val extension: String?,
)