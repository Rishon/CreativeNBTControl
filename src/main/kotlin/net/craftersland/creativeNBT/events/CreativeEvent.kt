package net.craftersland.creativeNBT.events

import net.craftersland.creativeNBT.CC
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.event.player.PlayerArmorStandManipulateEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SpawnEggMeta

class CreativeEvent(private val cc: CC) : Listener {
    @EventHandler(ignoreCancelled = true)
    fun inventoryDragEvent(event: InventoryDragEvent) {
        if (event.whoClicked.gameMode == GameMode.CREATIVE) {
            var newAmount = 0
            for ((_, value) in event.newItems) newAmount += value.amount
            if (newAmount > event.oldCursor.amount) event.isCancelled = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun inventoryClickEvent(event: InventoryClickEvent) {
        //Triggers on other inventory click
        if (event.click == ClickType.MIDDLE && event.click.isCreativeAction || event.click == ClickType.UNKNOWN) {
            if (event.inventory.type != InventoryType.PLAYER) {
                val p = event.whoClicked as Player
                if (p.gameMode == GameMode.CREATIVE) {
                    if (!p.hasPermission("CNC.bypass") && event.currentItem != null) {
                        val cursorItem = event.currentItem
                        if (cursorItem!!.type != Material.AIR) {
                            val amount = cursorItem.amount
                            val data = cursorItem.data!!.data.toShort()
                            var checkEnchants = true
                            if (cursorItem.itemMeta is SpawnEggMeta) {
                                event.cursor = ItemStack(cursorItem.type, amount, data)
                                checkEnchants = false
                            } else {
                                for (s in cc.getConfigHandler().getStringList("EnabledFor")!!) {
                                    try {
                                        if (cursorItem.type == Material.valueOf(s)) {
                                            event.cursor = ItemStack(Material.valueOf(s), amount, data)
                                            checkEnchants = false
                                            break
                                        }
                                    } catch (e: Exception) {
                                        CC.log!!.warning("Error on material: " + s + " Error: " + e.message)
                                    }
                                }
                                if (cursorItem.enchantments.isNotEmpty() && !p.hasPermission("CNC.bypass.enchants") && checkEnchants) {
                                    event.cursor = ItemStack(cursorItem.type, amount, data)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun inventoryCreativeEvent(event: InventoryCreativeEvent) {
        //Triggers on player inventory click
        if (event.click == ClickType.CREATIVE) {
            val p = event.whoClicked as Player
            if (!p.hasPermission("CNC.bypass")) {
                val cursorItem = event.cursor
                if (cursorItem.type != Material.AIR) {
                    val amount = cursorItem.amount
                    val data = cursorItem.data!!.data.toShort()
                    var checkEnchants = true
                    if (cursorItem.itemMeta is SpawnEggMeta) {
                        event.cursor = ItemStack(cursorItem.type, amount, data)
                        checkEnchants = false
                    } else {
                        for (s in cc.getConfigHandler().getStringList("EnabledFor")!!) {
                            try {
                                if (cursorItem.type == Material.valueOf(s)) {
                                    event.cursor = ItemStack(Material.valueOf(s), amount, data)
                                    checkEnchants = false
                                    break
                                }
                            } catch (e: Exception) {
                                CC.log!!.warning("Error on material: " + s + " Error: " + e.message)
                            }
                        }
                        if (cursorItem.enchantments.isNotEmpty() && !p.hasPermission("CNC.bypass.enchants") && checkEnchants) {
                            event.cursor = ItemStack(cursorItem.type, amount, data)
                        }
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        val p = event.player
        val entity = event.rightClicked
        val item = p.inventory.itemInMainHand
        if (item.type == Material.AIR) return
        if (entity.type == EntityType.ITEM_FRAME) {
            if (!p.hasPermission("CNC.bypass") && p.gameMode == GameMode.CREATIVE) {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        val p = event.player
        val item = event.playerItem
        if (event.armorStandItem.type == Material.AIR) {
            if (item.type == Material.AIR) return
            if (!p.hasPermission("CNC.bypass") && p.gameMode == GameMode.CREATIVE) {
                event.isCancelled = true
            }
        }
    }
}