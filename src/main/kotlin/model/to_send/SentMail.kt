package model.to_send

import kotlinx.serialization.Serializable

@Serializable
data class SentMail(
    val message: Message,
    val saveToSentItems: String
)