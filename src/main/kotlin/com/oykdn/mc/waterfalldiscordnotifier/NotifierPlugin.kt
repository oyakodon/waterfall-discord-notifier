package com.oykdn.mc.waterfalldiscordnotifier

import com.oykdn.mc.waterfalldiscordnotifier.channel.discord.DiscordChannel
import com.oykdn.mc.waterfalldiscordnotifier.config.Config
import com.oykdn.mc.waterfalldiscordnotifier.listener.BungeePlayerEventListener
import com.oykdn.mc.waterfalldiscordnotifier.listener.PlayerEventNotifier
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File


@Suppress("unused")
class NotifierPlugin : Plugin() {
    companion object {
        private const val CONFIG_PATH = "config.yml"
    }

    override fun onEnable() {
        logger.info("loading...")

        val config = loadConfig()

        val notifier = PlayerEventNotifier(
            config, listOf(DiscordChannel(config))
        )
        proxy.pluginManager.registerListener(
            this, BungeePlayerEventListener(notifier)
        )

        logger.info("initialized.")
    }

    /**
     * 設定ファイルを読み込む。
     * 設定ファイルがない場合はデフォルト値で新規作成する。
     */
    private fun loadConfig(): Config {
        val yamlConfig: Configuration = runCatching {
            ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(
                File(
                    dataFolder, CONFIG_PATH
                )
            )
        }.fold(onSuccess = { it }, onFailure = {
            if (!dataFolder.exists()) {
                dataFolder.mkdir()
            }

            val configuration = Config.default()
            ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(
                configuration, File(
                    dataFolder, CONFIG_PATH
                )
            )

            configuration
        })

        return Config.parse(yamlConfig)
    }
}
