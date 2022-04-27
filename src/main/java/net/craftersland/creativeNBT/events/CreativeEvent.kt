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
    fun InventoryDragEvent(event: InventoryDragEvent) {
        if (event.whoClicked.gameMode == GameMode.CREATIVE) {
            var newAmount = 0
            for ((_, value) in event.newItems) newAmount += value.amount

            //CC.log.warning("DragEvent - Type: " + event.getType().toString() + " - New Size: " + event.getNewItems().size() + " - New Amount: " + newAmount + " - Old Amount: " + event.getOldCursor().getAmount() + " - Result: " + event.getResult().toString());
            if (newAmount > event.oldCursor.amount) event.isCancelled = true
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun inventoryClickEvent(event: InventoryClickEvent) {
        //Triggers on other inventory click
        //CC.log.warning("Debug 1 - " + event.getClick().isCreativeAction() + " - " + event.getClick().isRightClick() + " - " + event.getClick().isLeftClick() + " - " + event.getClick() +  " - " + event.getClick().isKeyboardClick());
        if (event.click == ClickType.MIDDLE && event.click.isCreativeAction == true || event.click == ClickType.UNKNOWN) {
            if (event.inventory.type != InventoryType.PLAYER) {
                val p = event.whoClicked as Player
                if (p.gameMode == GameMode.CREATIVE) {
                    if (p.hasPermission("CNC.bypass") == false && event.currentItem != null) {
                        val cursorItem = event.currentItem
                        if (cursorItem!!.type != Material.AIR) {
                            val amount = cursorItem.amount
                            val data = cursorItem.data!!.data.toShort()
                            var checkEnchants = true
                            if (cursorItem.itemMeta is SpawnEggMeta) {
                                event.cursor = ItemStack(cursorItem.type, amount, data)
                                checkEnchants = false
                            } else {
                                for (s in cc.configHandler.getStringList("EnabledFor")) {
                                    try {
                                        if (cursorItem.type == Material.valueOf(s!!)) {
                                            event.cursor = ItemStack(Material.valueOf(s!!), amount, data)
                                            checkEnchants = false
                                            break
                                        }
                                    } catch (e: Exception) {
                                        CC.Companion.log!!.warning("Error on material: " + s + " Error: " + e.message)
                                    }
                                }
                                if (cursorItem.enchantments.isEmpty() == false && p.hasPermission("CNC.bypass.enchants") == false && checkEnchants == true) {
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
        //CC.log.warning("Debug 2 - " + event.getClick().isCreativeAction() + " - " + event.getClick().isRightClick() + " - " + event.getClick().isLeftClick() + " - " + event.getClick() + " - " + event.getSlotType() + " - " + event.getRawSlot() + " - " + event.getClickedInventory().getType().toString());
        if (event.click == ClickType.CREATIVE) {
            val p = event.whoClicked as Player
            if (p.hasPermission("CNC.bypass") == false && event.cursor != null) {
                val cursorItem = event.cursor
                if (cursorItem.type != Material.AIR) {
                    val amount = cursorItem.amount
                    val data = cursorItem.data!!.data.toShort()
                    var checkEnchants = true
                    if (cursorItem.itemMeta is SpawnEggMeta) {
                        event.setCursor(ItemStack(cursorItem.type, amount, data))
                        checkEnchants = false
                    } else {
                        for (s in cc.configHandler.getStringList("EnabledFor")) {
                            try {
                                if (cursorItem.type == Material.valueOf(s!!)) {
                                    event.setCursor(ItemStack(Material.valueOf(s!!), amount, data))
                                    checkEnchants = false
                                    break
                                }
                            } catch (e: Exception) {
                                CC.Companion.log!!.warning("Error on material: " + s + " Error: " + e.message)
                            }
                        }
                        if (cursorItem.enchantments.isEmpty() == false && p.hasPermission("CNC.bypass.enchants") == false && checkEnchants == true) {
                            event.setCursor(ItemStack(cursorItem.type, amount, data))
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
        if (item == null || item.type == Material.AIR) return
        if (entity.type == EntityType.ITEM_FRAME) {
            if (p.hasPermission("CNC.bypass") == false && p.gameMode == GameMode.CREATIVE) {
                event.isCancelled = true
                p.sendMessage(cc.configHandler.getStringWithColor("ChatMessages.OpenSurvivalFrame"))
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        val p = event.player
        val item = event.playerItem
        if (item != null && (event.armorStandItem == null || event.armorStandItem.type == Material.AIR)) {
            if (item.type == Material.AIR) return
            if (p.hasPermission("CNC.bypass") == false && p.gameMode == GameMode.CREATIVE) {
                event.isCancelled = true
                p.sendMessage(cc.configHandler.getStringWithColor("ChatMessages.OpenSurvivalArmor"))
            }
        }
    }
}