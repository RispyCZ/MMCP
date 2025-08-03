package cz.rispy.mmcp

import HTTPTransportServer
import org.bukkit.plugin.java.JavaPlugin

class MMCP : JavaPlugin() {
    private val config = Config(this).create()
    private val mcpServer = MCPServer(server, this)
    private val httpTransportServer = HTTPTransportServer(mcpServer.create(), config).create()

    override fun onEnable() {
        httpTransportServer.start()
    }

    override fun onDisable() {
        httpTransportServer.stop(500)
    }
}
