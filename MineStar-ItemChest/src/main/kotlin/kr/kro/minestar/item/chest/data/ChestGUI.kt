package kr.kro.minestar.item.chest.data

import kr.kro.minestar.item.chest.Main
import kr.kro.minestar.utility.event.disable
import kr.kro.minestar.utility.gui.GUI
import kr.kro.minestar.utility.inventory.InventoryUtil
import kr.kro.minestar.utility.item.Slot
import kr.kro.minestar.utility.item.SlotKey
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.sound.PlaySound
import kr.kro.minestar.utility.string.unColor
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.tags.ItemTagType

class ChestGUI(player: Player, private val chestItem: ItemStack) : GUI(player) {

    override val gui = InventoryUtil.gui(6, chestItem.display().unColor())
    override val pl = Main.pl

    private val nameKey = NamespacedKey(pl, "items")
    private val type = ItemTagType.STRING

    private val openSound = PlaySound().apply {
        sound = Sound.BLOCK_CHEST_OPEN
        soundCategory = SoundCategory.PLAYERS
    }

    private val closeSound = PlaySound().apply {
        sound = Sound.BLOCK_CHEST_CLOSE
        soundCategory = SoundCategory.PLAYERS
    }

    init {
        openGUI()
        openSound.play(player)
    }

    override fun displaying() {
        val meta = chestItem.itemMeta
        val tag = meta.customTagContainer

        val tagValue = tag.getCustomTag(nameKey, type) ?: return
        val yaml = YamlConfiguration().apply { loadFromString(tagValue) }
        for (key in yaml.getKeys(false)) {
            if (!yaml.isItemStack(key)) continue

            val slot = key.toIntOrNull() ?: continue
            val item = yaml.getItemStack(key) ?: continue

            if (slot < 0) continue
            if (gui.size <= slot) continue

            gui.setItem(slot, item)
        }
    }

    private fun save() {
        val meta = chestItem.itemMeta
        val tag = meta.customTagContainer

        val yaml = YamlConfiguration()
        for (slot in 0 until gui.size) yaml["$slot"] = gui.getItem(slot) ?: continue

        tag.setCustomTag(nameKey, type, yaml.saveToString())

        chestItem.itemMeta = meta
    }

    @EventHandler
    override fun clickGUI(e: InventoryClickEvent) {
        if (e.whoClicked != player) return
        if (e.inventory != gui) return

        val clickItem = e.currentItem

        if (clickItem != chestItem) return
        e.isCancelled = true
    }


    @EventHandler
    override fun closeGUI(e: InventoryCloseEvent) {
        if (e.player != player) return
        if (e.inventory != gui) return
        disable()
        save()
        closeSound.play(player)
    }


    override fun slots(): Map<out SlotKey, Slot> = mapOf()

}