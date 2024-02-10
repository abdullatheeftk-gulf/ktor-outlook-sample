package model.to_send

import kotlinx.serialization.Serializable

@Serializable
data class EmailAddress(
    val address: String
)