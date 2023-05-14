package com.oykdn.mc.waterfalldiscordnotifier.command

import com.oykdn.mc.waterfalldiscordnotifier.config.ConfigLoader
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.PluginDescription
import net.md_5.bungee.api.plugin.TabExecutor


class NotifierCommand(
    private val config: ConfigLoader,
    private val description: PluginDescription,
) : Command(
    "wfdiscordnotifier", "wfdiscordnotifier.command", "nt"
), TabExecutor {
    companion object {
        private const val SUBCOMMAND_RELOAD = "reload"
        private const val SUBCOMMAND_TOGGLE = "toggle"
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        return if (args?.size == 1) arrayListOf(SUBCOMMAND_RELOAD, SUBCOMMAND_TOGGLE) else arrayListOf()
    }

    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        sender ?: return

        if (args.isNullOrEmpty()) {
            ComponentBuilder("Waterfall Discord Notifier: v${description.version}").color(ChatColor.GRAY).create()
                .forEach(sender::sendMessage)
            return
        }

        when (args.first()) {
            SUBCOMMAND_RELOAD -> reload(sender)
            SUBCOMMAND_TOGGLE -> toggle(sender)
        }
    }

    private fun reload(sender: CommandSender) {
        config.get(force = true)

        ComponentBuilder("wfdiscordnotifier: reloaded!").color(ChatColor.DARK_GREEN).create()
            .forEach(sender::sendMessage)
    }

    private fun toggle(sender: CommandSender) {
        config.get().enabled = !config.get().enabled
        config.save()

        val enabled = if (config.get().enabled) "enabled" else "disabled"
        ComponentBuilder("wfdiscordnotifier: toggled to [$enabled]").color(ChatColor.DARK_GREEN).create()
            .forEach(sender::sendMessage)
    }
}
