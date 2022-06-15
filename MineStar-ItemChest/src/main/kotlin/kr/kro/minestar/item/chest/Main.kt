package kr.kro.minestar.item.chest

import kr.kro.minestar.item.chest.function.events.AlwaysEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var pl: Main
        const val prefix = "§f[§9ItemChest§f]"
    }

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("itemchest")?.setExecutor(Command)

        AlwaysEvent
    }

    override fun onDisable() {
    }
}