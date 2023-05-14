package com.oykdn.mc.waterfalldiscordnotifier.model

import java.util.UUID

/**
 * 通知内容
 */
data class Notification(
    val name: String, // プレイヤー名
    val uuid: UUID, // プレイヤーUUID
    val type: PlayerEventType, // イベント種別
    val to: String?, // 移動先サーバ名
)
