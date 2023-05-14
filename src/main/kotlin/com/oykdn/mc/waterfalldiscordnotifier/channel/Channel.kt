package com.oykdn.mc.waterfalldiscordnotifier.channel

import com.oykdn.mc.waterfalldiscordnotifier.model.Notification

interface Channel {
    fun send(n: Notification)
}
