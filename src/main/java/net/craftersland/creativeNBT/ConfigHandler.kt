package net.craftersland.creativeNBT

import java.io.File

class ConfigHandler(private val eco: CC) {
    init {
        loadConfig()
    }

    fun loadConfig() {
        val pluginFolder = File("plugins" + System.getProperty("file.separator") + eco.description.name)
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir()
        }
        val configFile =
            File("plugins" + System.getProperty("file.separator") + eco.description.name + System.getProperty("file.separator") + "config.yml")
        if (!configFile.exists()) {
            CC.log!!.info("No config file found! Creating new one...")
            eco.saveDefaultConfig()
        }
        try {
            CC.log!!.info("Loading the config file...")
            eco.config.load(configFile)
            CC.log!!.info("Loading complete!")
        } catch (e: Exception) {
            CC.log!!.severe("Could not load the config file! You need to regenerate the config! Error: " + e.message)
            e.printStackTrace()
        }
    }

    fun getString(key: String): String {
        return if (!eco.config.contains(key)) {
            eco.logger.severe("Could not locate " + key + " in the config.yml inside of the " + eco.description.name + " folder! (Try generating a new one by deleting the current)")
            "errorCouldNotLocateInConfigYml:$key"
        } else {
            eco.config.getString(key)!!
        }
    }

    fun getStringWithColor(key: String): String {
        return if (!eco.config.contains(key)) {
            eco.logger.severe("Could not locate " + key + " in the config.yml inside of the " + eco.description.name + " folder! (Try generating a new one by deleting the current)")
            "errorCouldNotLocateInConfigYml:$key"
        } else {
            eco.config.getString(key)!!.replace("&".toRegex(), "§")
        }
    }

    fun getStringList(key: String): List<String>? {
        return if (!eco.config.contains(key)) {
            eco.logger.severe("Could not locate " + key + " in the config.yml inside of the " + eco.description.name + " folder! (Try generating a new one by deleting the current)")
            null
        } else {
            eco.config.getStringList(key)
        }
    }

    fun getInteger(key: String): Int? {
        return if (!eco.config.contains(key)) {
            eco.logger.severe("Could not locate " + key + " in the config.yml inside of the " + eco.description.name + " folder! (Try generating a new one by deleting the current)")
            null
        } else {
            eco.config.getInt(key)
        }
    }

    fun getBoolean(key: String): Boolean? {
        return if (!eco.config.contains(key)) {
            eco.logger.severe("Could not locate " + key + " in the config.yml inside of the " + eco.description.name + " folder! (Try generating a new one by deleting the current)")
            null
        } else {
            eco.config.getBoolean(key)
        }
    }
}