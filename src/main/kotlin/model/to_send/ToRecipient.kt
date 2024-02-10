package model.to_send

import kotlinx.serialization.Serializable

@Serializable
data class ToRecipient(
    val emailAddress: EmailAddress
)