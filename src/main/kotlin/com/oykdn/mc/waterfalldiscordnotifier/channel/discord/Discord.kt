package com.oykdn.mc.waterfalldiscordnotifier.channel.discord

import com.oykdn.mc.waterfalldiscordnotifier.channel.Channel
import com.oykdn.mc.waterfalldiscordnotifier.model.DiscordWebhookPayload
import com.oykdn.mc.waterfalldiscordnotifier.model.Notification
import net.md_5.bungee.api.ProxyServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class Discord(
    private val isDebug: Boolean,
    private val webhookId: String,
    private val webhookToken: String,
) : Channel {
    companion object {
        private const val BASE_URL = "https://discord.com/api/webhooks/"
    }

    abstract override fun send(n: Notification)

    private val logger = ProxyServer.getInstance().logger

    /**
     * okHttpクライアント
     * デバッグモードの際はボディもログ出力する。
     */
    private val client = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().setLevel(if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    ).build()

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create()).client(client).build()

    fun post(message: DiscordWebhookPayload) {
        // 必要な設定がない場合は何もしない
        if (webhookId.isEmpty() || webhookToken.isEmpty()) {
            logger.warning("discord: webhook id/token are invalid.")
            return
        }

        // POST処理
        // エラーの場合はログに出力
        runCatching {
            retrofit.create(DiscordWebhookService::class.java).post(
                webhookId, webhookToken, message
            ).execute()
        }.apply {
            onSuccess {
                if (!it.isSuccessful) {
                    logger.warning("discord: failed to post with code=${it.code()}")
                }
            }
            onFailure {
                logger.warning("discord: " + it.message)
            }
        }
    }
}
