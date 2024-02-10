package model.to_send

import kotlinx.serialization.Serializable

@Serializable
data class CcRecipient(
    val emailAddress: EmailAddress
)