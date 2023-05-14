package com.oykdn.mc.waterfalldiscordnotifier.channel.discord

import com.oykdn.mc.waterfalldiscordnotifier.config.ConfigLoader
import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookEmbed
import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookEmbedField
import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookEmbedThumbnail
import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookPayload
import com.oykdn.mc.waterfalldiscordnotifier.model.Notification
import com.oykdn.mc.waterfalldiscordnotifier.model.PlayerEventType
import java.time.OffsetDateTime
import java.time.ZoneId

class DiscordChannel(
    config: ConfigLoader,
) : Discord(
    config.get().isDebug, config.get().discordWebhookId, config.get().discordWebhookToken
) {
    companion object {
        const val URL_AVATAR_HEAD = "https://crafatar.com/renders/head/"
    }

    override fun send(n: Notification) {
        // イベント種別ごとにメッセージと枠の色を分ける
        val (message, color) = when (n.type) {
            PlayerEventType.ProxyJoined -> "${n.name} さんがサーバに参加しました！" to 9095002
            PlayerEventType.ServerSwitched -> "${n.name} さんがサーバを移動しました！" to 11395310
            PlayerEventType.ProxyLeft -> "${n.name} さんがサーバから退出しました。" to 16092797
        }

        // Leftイベント以外は移動先のサーバ情報も付ける
        val fields = listOfNotNull(n.to?.let {
            DiscordWebhookEmbedField(
                "サーバ", "`$it`", true
            )
        })

        val embeds = listOf(
            DiscordWebhookEmbed(
                message,
                OffsetDateTime.now(ZoneId.of("Asia/Tokyo")).toString(),
                color,
                DiscordWebhookEmbedThumbnail(
                    "$URL_AVATAR_HEAD/${n.uuid}"
                ),
                fields,
            )
        )

        // DiscordにWebhookで送信
        post(DiscordWebhookPayload(embeds))
    }
}
