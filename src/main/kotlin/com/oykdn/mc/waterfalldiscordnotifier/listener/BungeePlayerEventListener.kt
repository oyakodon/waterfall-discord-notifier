package com.oykdn.mc.waterfalldiscordnotifier.listener

import com.oykdn.mc.waterfalldiscordnotifier.model.Notification
import com.oykdn.mc.waterfalldiscordnotifier.model.PlayerEventType
import net.md_5.bungee.api.event.PlayerDisconnectEvent
import net.md_5.bungee.api.event.ServerSwitchEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class BungeePlayerEventListener(
    private val notifier: Notifier,
) : Listener {
    /**
     * プレイヤーが接続先のサーバーを変更した時
     */
    @EventHandler
    fun onServerSwitch(event: ServerSwitchEvent) {
        val serverInfo = event.player.server.info

        notifier.send(Notification(event.player.name,
            event.player.uniqueId,
            event.from?.let { PlayerEventType.ServerSwitched } ?: PlayerEventType.ProxyJoined,
            serverInfo.name, serverInfo.players.size))
    }

    /**
     * プレイヤーがプロキシを離れた時
     */
    @EventHandler
    fun onPlayerDisconnect(event: PlayerDisconnectEvent) {
        notifier.send(
            Notification(
                event.player.name, event.player.uniqueId, PlayerEventType.ProxyLeft, null, null
            )
        )
    }
}