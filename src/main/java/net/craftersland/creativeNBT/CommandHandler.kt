package net.craftersland.creativeNBT

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandHandler(private val plugin: CC) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, cmdlabel: String, args: Array<String>): Boolean {
        if (cmdlabel.equals("cnc", ignoreCase = true) == true) {
            if (args.size == 1) {
                if (args[0].equals("reload", ignoreCase = true) == true) {
                    if (sender is Player) {
                        if (sender.hasPermission("CNC.admin") == false) {
                            plugin.soundHandler.sendPlingSound(sender)
                            sender.sendMessage(plugin.configHandler.getStringWithColor("ChatMessages.NoPermission"))
                            return true
                        } else {
                            plugin.soundHandler.sendPlingSound(sender)
                            sender.sendMessage(plugin.configHandler.getStringWithColor("ChatMessages.CmdReload"))
                        }
                    }
                    plugin.configHandler.loadConfig()
                }
            } else {
                if (sender is Player) {
                    plugin.soundHandler.sendConfirmSound(sender)
                }
                sender.sendMessage(plugin.configHandler.getStringWithColor("ChatMessages.CmdHelp"))
            }
        }
        return true
    }
}