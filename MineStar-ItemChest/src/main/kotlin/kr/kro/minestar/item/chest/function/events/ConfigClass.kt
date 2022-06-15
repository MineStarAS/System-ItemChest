package kr.kro.minestar.item.chest.function.events

import kr.kro.minestar.item.chest.Main.Companion.pl
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import kotlin.apply


class ConfigClass {
    private val file = File(pl.dataFolder, "config.yml").apply {
        if (!exists()) pl.saveResource("config.yml", false)
    }

    private var config = YamlConfiguration.loadConfiguration(file)

    val displayTag = config.getString("tag")
    val material = config.getString("material")
}