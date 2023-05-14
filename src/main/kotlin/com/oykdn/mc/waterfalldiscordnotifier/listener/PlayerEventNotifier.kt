package com.oykdn.mc.waterfalldiscordnotifier.listener

import com.oykdn.mc.waterfalldiscordnotifier.channel.Channel
import com.oykdn.mc.waterfalldiscordnotifier.config.Config
import com.oykdn.mc.waterfalldiscordnotifier.model.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.md_5.bungee.api.ProxyServer

class PlayerEventNotifier(
    private val config: Config,
    private val channels: List<Channel>,
) : Notifier {
    private val logger = ProxyServer.getInstance().logger
    private val scope = CoroutineScope(Dispatchers.IO)

    private suspend fun push(n: Notification) = withContext(Dispatchers.IO) {
        channels.forEach { it.send(n) }
    }

    override fun send(n: Notification) {
        if (!config.enabled) {
            logger.warning("notifier: DISABLED in config, nothing sent.")
            return
        }

        logger.info("notifier: sending notification: $n")

        scope.launch {
            push(n)
        }
    }
}