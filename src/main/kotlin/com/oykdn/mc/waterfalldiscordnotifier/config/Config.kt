package com.oykdn.mc.waterfalldiscordnotifier.config

import net.md_5.bungee.config.Configuration

data class Config(
    var enabled: Boolean = true,
    var isDebug: Boolean = false,
    val discordWebhookId: String = "",
    val discordWebhookToken: String = "",
    val colorJoined: Int = 0,
    val templateJoined: String = "",
    val colorSwitched: Int = 0,
    val templateSwitched: String = "",
    val colorLeft: Int = 0,
    val templateLeft: String = "",

    ) {
    companion object {
        private const val KEY_ENABLED = "enabled"
        private const val KEY_DEBUG = "debug"
        private const val KEY_DISCORD_WEBHOOK_ID = "discord.webhookId"
        private const val KEY_DISCORD_WEBHOOK_TOKEN = "discord.webhookToken"
        private const val KEY_MSG_JOINED_COLOR = "message.joined.color"
        private const val KEY_MSG_JOINED_TEMPLATE = "message.joined.template"
        private const val KEY_MSG_SWITCHED_COLOR = "message.switched.color"
        private const val KEY_MSG_SWITCHED_TEMPLATE = "message.switched.template"
        private const val KEY_MSG_LEFT_COLOR = "message.left.color"
        private const val KEY_MSG_LEFT_TEMPLATE = "message.left.template"

        fun load(conf: Configuration): Config {
            return Config(
                enabled = conf.getBoolean(KEY_ENABLED, true),
                isDebug = conf.getBoolean(KEY_DEBUG, false),
                discordWebhookId = conf.getString(KEY_DISCORD_WEBHOOK_ID, ""),
                discordWebhookToken = conf.getString(KEY_DISCORD_WEBHOOK_TOKEN, ""),
                colorJoined = conf.getInt(KEY_MSG_JOINED_COLOR, 0),
                templateJoined = conf.getString(KEY_MSG_JOINED_TEMPLATE, ""),
                colorSwitched = conf.getInt(KEY_MSG_SWITCHED_COLOR, 0),
                templateSwitched = conf.getString(KEY_MSG_SWITCHED_TEMPLATE, ""),
                colorLeft = conf.getInt(KEY_MSG_LEFT_COLOR, 0),
                templateLeft = conf.getString(KEY_MSG_LEFT_TEMPLATE, ""),
            )
        }
    }

    fun save(c: Configuration) = c.apply {
        set(KEY_ENABLED, enabled)
        set(KEY_DEBUG, isDebug)
        set(KEY_DISCORD_WEBHOOK_ID, discordWebhookId)
        set(KEY_DISCORD_WEBHOOK_TOKEN, discordWebhookToken)
        set(KEY_MSG_JOINED_COLOR, colorJoined)
        set(KEY_MSG_JOINED_TEMPLATE, templateJoined)
        set(KEY_MSG_SWITCHED_COLOR, colorSwitched)
        set(KEY_MSG_SWITCHED_TEMPLATE, templateSwitched)
        set(KEY_MSG_LEFT_COLOR, colorLeft)
        set(KEY_MSG_LEFT_TEMPLATE, templateLeft)
    }
}
