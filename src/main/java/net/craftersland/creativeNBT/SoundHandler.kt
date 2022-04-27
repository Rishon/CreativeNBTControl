package net.craftersland.creativeNBT

import org.bukkit.Sound
import org.bukkit.entity.Player

class SoundHandler(private val `as`: CC) {
    fun sendPlingSound(p: Player) {
        if (`as`.is13Server == true) {
            p.playSound(p.location, Sound.BLOCK_NOTE_BLOCK_PLING, 3f, 3f)
        } else if (`as`.is19Server == true) {
            p.playSound(p.location, Sound.valueOf("BLOCK_NOTE_PLING"), 3f, 3f)
        } else {
            p.playSound(p.location, Sound.valueOf("NOTE_PLING"), 3f, 3f)
        }
    }

    fun sendLevelUpSound(p: Player) {
        if (`as`.is19Server == true) {
            p.playSound(p.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        } else {
            p.playSound(p.location, Sound.valueOf("LEVEL_UP"), 1f, 1f)
        }
    }

    fun sendConfirmSound(p: Player) {
        if (`as`.is19Server == true) {
            p.playSound(p.location, Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1f)
        } else {
            p.playSound(p.location, Sound.valueOf("SUCCESSFUL_HIT"), 1f, 1f)
        }
    }
}