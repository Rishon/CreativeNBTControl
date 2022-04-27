package net.craftersland.creativeNBT

import java.io.File

class ConfigHandler(private val eco: CC) {

    init {
        loadConfig()
    }

    fun loadConfig() {
        val configFile = File(eco.dataFolder, "config.yml")
        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()
            eco.saveResource("config.yml", false)
        }
        eco.config.load(configFile)
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