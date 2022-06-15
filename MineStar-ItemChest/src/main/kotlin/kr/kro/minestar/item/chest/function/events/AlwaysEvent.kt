package kr.kro.minestar.item.chest.function.events

import kr.kro.minestar.item.chest.Main.Companion.pl
import kr.kro.minestar.item.chest.data.ChestGUI
import kr.kro.minestar.utility.event.enable
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.string.toPlayer
import kr.kro.minestar.utility.string.toServer
import kr.kro.minestar.utility.string.unColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

object AlwaysEvent : Listener {

    init {
        enable(pl)
    }

    @EventHandler
    fun playerUseItem(e: PlayerInteractEvent) {
        if (e.hand != EquipmentSlot.HAND) return
        if (!e.action.isRightClick) return
        val chestItem = e.item ?: return

        if (!isChestItem(chestItem)) return

        e.isCancelled = true

        ChestGUI(e.player, chestItem)
    }

    private fun isChestItem(item: ItemStack): Boolean {
        val config = ConfigClass()
        val display = item.display().unColor()
        val displayTag = config.displayTag ?: return false

        when (displayTag) {
            "", " " -> return false
        }
        if (!display.contains(displayTag)) return false
        val materialName = config.material ?: return false
        val material = Material.getMaterial(materialName.uppercase()) ?: return false
        if (item.type != material) return false
        return true
    }
}