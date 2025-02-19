package org.koolda.files.serializable

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val link: Link,
    val server: Server,
)