package com.oykdn.mc.waterfalldiscordnotifier.config

import net.md_5.bungee.config.Configuration

data class Config(
    val enabled: Boolean,
    val isDebug: Boolean,
    val discordWebhookId: String,
    val discordWebhookToken: String,
) {
    companion object {
        private const val KEY_DEBUG = "debug"
        private const val KEY_DISCORD_WEBHOOK_ID = "discord.webhookId"
        private const val KEY_DISCORD_WEBHOOK_TOKEN = "discord.webhookToken"
        private const val KEY_ENABLED = "enabled"

        fun parse(conf: Configuration): Config {
            return Config(
                enabled = conf.getBoolean(KEY_ENABLED, true),
                isDebug = conf.getBoolean(KEY_DEBUG, false),
                discordWebhookId = conf.getString(KEY_DISCORD_WEBHOOK_ID, ""),
                discordWebhookToken = conf.getString(KEY_DISCORD_WEBHOOK_TOKEN, ""),
            )
        }

        fun default(): Configuration {
            return Configuration().apply {
                set(KEY_ENABLED, true)
                set(KEY_DEBUG, false)
                set(KEY_DISCORD_WEBHOOK_ID, "")
                set(KEY_DISCORD_WEBHOOK_TOKEN, "")
            }
        }
    }
}
