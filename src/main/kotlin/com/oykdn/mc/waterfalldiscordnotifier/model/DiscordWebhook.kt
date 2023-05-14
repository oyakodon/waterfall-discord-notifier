package com.oykdn.mc.waterfalldiscordnotifier.model

data class DiscordWebhookEmbedThumbnail(
    val url: String,
)

data class DiscordWebhookEmbedField(
    val name: String?,
    val value: String?,
    val inline: Boolean,
)

data class DiscordWebhookEmbed(
    val title: String,
    val timestamp: String?,
    val color: Int,
    val thumbnail: DiscordWebhookEmbedThumbnail,
    val fields: List<DiscordWebhookEmbedField>,
)

/**
 * Discord Webhook Payload (embedsのみ)
 *
 * [Developer Portal](https://discord.com/developers/docs/resources/webhook#execute-webhook)
 */
data class DiscordWebhookPayload(
    val embeds: List<DiscordWebhookEmbed>,
)
