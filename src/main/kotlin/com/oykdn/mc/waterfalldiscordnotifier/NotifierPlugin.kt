package com.oykdn.mc.waterfalldiscordnotifier

import com.oykdn.mc.waterfalldiscordnotifier.channel.discord.DiscordChannel
import com.oykdn.mc.waterfalldiscordnotifier.command.NotifierCommand
import com.oykdn.mc.waterfalldiscordnotifier.config.ConfigLoader
import com.oykdn.mc.waterfalldiscordnotifier.listener.BungeePlayerEventListener
import com.oykdn.mc.waterfalldiscordnotifier.listener.PlayerEventNotifier
import net.md_5.bungee.api.plugin.Plugin


@Suppress("unused")
class NotifierPlugin : Plugin() {
    companion object {
        private const val CONFIG_PATH = "config.yml"
    }

    override fun onEnable() {
        logger.info("loading...")


        val config = ConfigLoader(dataFolder, CONFIG_PATH)

        val notifier = PlayerEventNotifier(
            config, listOf(DiscordChannel(config))
        )

        proxy.pluginManager.let {
            it.registerListener(this, BungeePlayerEventListener(notifier))
            it.registerCommand(this, NotifierCommand(config, description))
        }

        logger.info("initialized.")
    }
}
