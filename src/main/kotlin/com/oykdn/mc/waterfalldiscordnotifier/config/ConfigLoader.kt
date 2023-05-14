package com.oykdn.mc.waterfalldiscordnotifier.config

import net.md_5.bungee.config.Configuration
import net.md_5.bungee.config.ConfigurationProvider
import net.md_5.bungee.config.YamlConfiguration
import java.io.File

class ConfigLoader(
    private val dataFolder: File,
    private val configPath: String,
) {
    private var config: Config? = null

    /**
     * 設定を返す。
     * 未ロードならファイルから読み込み、ファイルもない場合はデフォルト値で新規作成する。
     *
     * @param force trueの場合、ファイルから強制的にロードする
     */
    fun get(force: Boolean = false): Config {
        if (force) {
            config = null
        }
        return config ?: load()
    }

    /**
     * 設定ファイルを読み込む。
     * 設定ファイルがない場合はデフォルト値で新規作成する。
     */
    private fun load(): Config {
        return (runCatching {
            Config.load(
                ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(
                    File(
                        dataFolder, configPath
                    )
                )
            )
        }.fold(onSuccess = { it }, onFailure = {
            if (!dataFolder.exists()) {
                dataFolder.mkdir()
            }
            save()
        })).also {
            config = it
        }
    }

    /**
     * 設定ファイルに設定を保存する。
     */
    fun save(): Config {
        val c = config ?: Config()
        ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(
            Configuration().also(c::save), File(
                dataFolder, configPath
            )
        )

        return c
    }
}
