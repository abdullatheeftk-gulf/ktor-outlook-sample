package model.to_send

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val body: Body,
    val ccRecipients: List<CcRecipient>,
    val subject: String,
    val toRecipients: List<ToRecipient>
)