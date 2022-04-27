package net.craftersland.creativeNBT

import net.craftersland.creativeNBT.events.CreativeEvent
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class CC : JavaPlugin() {

    companion object {
        var log: Logger? = null
        var configHandler: ConfigHandler? = null
        var soundHandler: SoundHandler? = null
    }

    override fun onEnable() {

        configHandler = ConfigHandler(this)
        soundHandler = SoundHandler(this)

        this.registerCommands();
        this.registerListeners();
        this.logger.info("${this.description.name} loaded successfully!")
    }

    override fun onDisable() {
        Bukkit.getScheduler().cancelTasks(this)
        HandlerList.unregisterAll(this)
        this.logger.info("${this.description.name} is disabled!")
    }

    private fun registerListeners() {
        val pm = server.pluginManager
        pm.registerEvents(CreativeEvent(this), this)
    }

    private fun registerCommands() {
        val cH = CommandHandler(this)
        this.getCommand("cnc")!!.setExecutor(cH)
    }

    fun getConfigHandler(): ConfigHandler {
        return configHandler!!
    }

    fun getSoundHandler(): SoundHandler {
        return soundHandler!!
    }
}