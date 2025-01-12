package org.koolda.files.serializable

import kotlinx.serialization.Serializable

@Serializable
data class Server(
    val ip: String
)
