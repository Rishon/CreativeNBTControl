package net.craftersland.creativeNBT

import net.craftersland.creativeNBT.events.CreativeEvent
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class CC : JavaPlugin() {
    var is19Server = true
    var isIt18Server = true
    var is13Server = true
    override fun onEnable() {
        log = logger
        mcVersion
        configHandler = ConfigHandler(this)
        soundHandler = SoundHandler(this)
        //Register Listeners
        val pm = server.pluginManager
        pm.registerEvents(CreativeEvent(this), this)
        val cH = CommandHandler(this)
        getCommand("cnc")!!.setExecutor(cH)
        log!!.info(pluginName + " loaded successfully!")
    }

    override fun onDisable() {
        Bukkit.getScheduler().cancelTasks(this)
        HandlerList.unregisterAll(this)
        log!!.info(pluginName + " is disabled!")
    }

    private val mcVersion: Unit
        private get() {
            val serverVersion =
                Bukkit.getBukkitVersion().split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val version = serverVersion[0]
            if (version.matches("1.7.10")) {
                isIt18Server = false
                is19Server = false
                is13Server = false
            }
            if (version.matches("1.7.10") || version.matches("1.7.9") || version.matches("1.7.5") || version.matches("1.7.2") || version.matches(
                    "1.8.8"
                ) || version.matches("1.8.3") || version.matches("1.8.4") || version.matches("1.8")
            ) {
                is19Server = false
                is13Server = false
            }
        }

    companion object {
        var log: Logger? = null
        var pluginName = "CreativeNbtControl"
        var configHandler: ConfigHandler? = null
            get() = Companion.field
            private set
        var soundHandler: SoundHandler? = null
            get() = Companion.field
            private set
    }
}