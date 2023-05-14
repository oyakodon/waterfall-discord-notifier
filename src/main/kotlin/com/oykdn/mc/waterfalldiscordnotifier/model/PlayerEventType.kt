package com.oykdn.mc.waterfalldiscordnotifier.model

/**
 * プレイヤーイベント種別
 */
enum class PlayerEventType {
    ProxyJoined, // プロキシサーバに参加 (接続)
    ServerSwitched, // サーバを移動
    ProxyLeft // プロキシサーバから切断
}
