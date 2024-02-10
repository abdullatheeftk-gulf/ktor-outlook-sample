package model.to_send

import kotlinx.serialization.Serializable

@Serializable
data class Body(
    val content: String,
    val contentType: String
)