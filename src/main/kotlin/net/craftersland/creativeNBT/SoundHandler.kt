package net.craftersland.creativeNBT

import org.bukkit.Sound
import org.bukkit.entity.Player

class SoundHandler(private val `as`: CC) {
    fun sendPlingSound(p: Player) {
        p.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_PLING, 3f, 3f)
    }

    fun sendLevelUpSound(p: Player) {
        p.playSound(p.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
    }

    fun sendConfirmSound(p: Player) {
        p.playSound(p.location, Sound.ENTITY_ARROW_HIT, 1f, 1f)
    }
}