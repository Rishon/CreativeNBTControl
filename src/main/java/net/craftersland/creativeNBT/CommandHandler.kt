package net.craftersland.creativeNBT

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandHandler(private val plugin: CC) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, cmdlabel: String, args: Array<String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player to use this command!")
            return true
        }

        if (args.size == 1) {
            if (args[0].equals("reload", ignoreCase = true)) {
                if (!sender.hasPermission("CNC.admin")) {
                    plugin.getSoundHandler().sendPlingSound(sender)
                    sender.sendMessage(plugin.getConfigHandler().getStringWithColor("ChatMessages.NoPermission"))
                    return true
                } else {
                    plugin.getSoundHandler().sendPlingSound(sender)
                    sender.sendMessage(plugin.getConfigHandler().getStringWithColor("ChatMessages.CmdReload"))
                }
                plugin.getConfigHandler().loadConfig()
            }
        } else {
            plugin.getSoundHandler().sendConfirmSound(sender)
            sender.sendMessage(plugin.getConfigHandler().getStringWithColor("ChatMessages.CmdHelp"))
        }
        return true
    }
}