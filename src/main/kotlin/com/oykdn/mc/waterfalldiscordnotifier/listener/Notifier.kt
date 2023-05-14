package com.oykdn.mc.waterfalldiscordnotifier.listener

import com.oykdn.mc.waterfalldiscordnotifier.model.Notification

interface Notifier {
    fun send(n: Notification)
}
