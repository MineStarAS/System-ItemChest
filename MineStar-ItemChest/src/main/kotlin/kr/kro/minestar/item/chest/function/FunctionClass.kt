package kr.kro.minestar.item.chest.function

import kr.kro.minestar.item.chest.Main.Companion.pl
import kr.kro.minestar.item.chest.function.events.ConfigClass
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.tags.ItemTagType
import java.util.*

object FunctionClass {
    fun getChest(itemName: String): ItemStack? {
        val config = ConfigClass()

        val materialName = config.material ?: return null
        val material = Material.getMaterial(materialName.uppercase()) ?: return null

        val displayTag = config.displayTag ?: return null

        val item = material.item().display("$displayTag $itemName")

        val nameKey = NamespacedKey(pl, "uuid")
        val type = ItemTagType.STRING

        val meta = item.itemMeta
        val tag = meta.customTagContainer

        val uuid = UUID.randomUUID().toString()

        tag.setCustomTag(nameKey, type, uuid)

        item.itemMeta = meta

        return item
    }
}