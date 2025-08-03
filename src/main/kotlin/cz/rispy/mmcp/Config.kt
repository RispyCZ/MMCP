package cz.rispy.mmcp

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import com.typesafe.config.ConfigValueFactory
import cz.rispy.mmcp.utils.FileUtils
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import org.apache.commons.lang3.RandomStringUtils
import org.bukkit.plugin.Plugin

class Config(private val bukkitPlugin: Plugin) {
    companion object {
        const val API_TOKEN_LENGTH: Int = 100
        const val CONFIG_FILE_NAME: String = "main.conf"
    }

    private val pluginConfigPath = Path("${bukkitPlugin.dataFolder}/${CONFIG_FILE_NAME}")
    private val configRenderOps = ConfigRenderOptions.defaults()
        .setJson(false)
        .setFormatted(true)
        .setComments(false)
        .setOriginComments(false)

    fun createPluginFolder() {
        val pluginDataPath = Path(bukkitPlugin.dataFolder.path)
        if (SystemFileSystem.exists(pluginDataPath)) return
        SystemFileSystem.createDirectories(pluginDataPath, true)
    }

    private fun getConfigDefaults(): Config {
        val configResource = bukkitPlugin.getResource(CONFIG_FILE_NAME)!!
        val configDefaults = ConfigFactory.parseString(
            configResource
                .bufferedReader()
                .use { it.readText() }) ?: throw Exception("Can't load config defaults")

        // Set default API Token
        val apiTokenValue = ConfigValueFactory.fromAnyRef(RandomStringUtils.randomAlphanumeric(API_TOKEN_LENGTH))
        return configDefaults.withValue("plugin.http.apiToken", apiTokenValue)
    }

    fun create(): Config {
        var pluginConfig: Config? = null
        try {
            val pluginConfigLines = mutableListOf<String>()
            FileUtils.readFirstLinesFromFile(pluginConfigPath, 1000, pluginConfigLines)
            pluginConfig =
                ConfigFactory.parseString(pluginConfigLines.joinToString("\n")).withFallback(getConfigDefaults())
            createPluginFolder()
            FileUtils.writeStringIntoFile(pluginConfigPath, pluginConfig.root().render(configRenderOps))
        } catch (e: Exception) {
            bukkitPlugin.logger.warning(e.message)
        }

        return pluginConfig ?: throw Exception("Config can't be null after loading.")
    }
}