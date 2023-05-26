package com.oykdn.mc.waterfalldiscordnotifier.channel.discord

import com.oykdn.mc.waterfalldiscordnotifier.config.ConfigLoader
import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookEmbed
import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookEmbedField
import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookEmbedThumbnail
import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookPayload
import com.oykdn.mc.waterfalldiscordnotifier.model.Notification
import com.oykdn.mc.waterfalldiscordnotifier.model.PlayerEventType
import net.md_5.bungee.api.ProxyServer
import java.time.OffsetDateTime
import java.time.ZoneId

class DiscordChannel(
    val config: ConfigLoader,
) : Discord(
    config.get().isDebug, config.get().discordWebhookId, config.get().discordWebhookToken
) {
    companion object {
        const val URL_AVATAR = "https://crafatar.com/renders/head/%s?overlay"
    }

    private val logger = ProxyServer.getInstance().logger

    override fun send(n: Notification) {
        // イベント種別ごとにメッセージと枠の色を分ける
        var c = config.get()
        val (message, color) = when (n.type) {
            PlayerEventType.ProxyJoined -> c.templateJoined.format(n.name) to c.colorJoined
            PlayerEventType.ServerSwitched -> c.templateSwitched.format(n.name) to c.colorSwitched
            PlayerEventType.ProxyLeft -> c.templateLeft.format(n.name) to c.colorLeft
        }

        if (message.isNullOrBlank() && color == 0) {
            logger.severe("notifier: invalid config (template, color)")
            return
        }

        val fields = mutableListOf<DiscordWebhookEmbedField>()

        // Left以外の場合、追加情報を通知に付与
        n.to?.let {
            // サーバ名
            fields.add(
                DiscordWebhookEmbedField(
                    "サーバ", "`$it`", true
                )
            )

            // 参加プレイヤー数
            fields.add(
                DiscordWebhookEmbedField(
                    "参加プレイヤー数", "`${n.playerCountInServer}`", true
                )
            )
        }

        val embeds = listOf(
            DiscordWebhookEmbed(
                message,
                OffsetDateTime.now(ZoneId.of("Asia/Tokyo")).toString(),
                color,
                DiscordWebhookEmbedThumbnail(
                    URL_AVATAR.format(n.uuid)
                ),
                fields,
            )
        )

        // DiscordにWebhookで送信
        post(DiscordWebhookPayload(embeds))
    }
}
