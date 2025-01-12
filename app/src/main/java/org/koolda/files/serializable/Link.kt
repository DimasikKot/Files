package org.koolda.files.serializable

import kotlinx.serialization.Serializable


@Serializable
data class Link(
    val disk: String,
    val path: String,
    val file: String
)