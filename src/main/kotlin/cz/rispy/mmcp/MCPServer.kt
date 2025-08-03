package cz.rispy.mmcp

import cz.rispy.mmcp.tools.readServerLog
import cz.rispy.mmcp.tools.runCommandAsConsole
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import org.bukkit.plugin.Plugin

class MCPServer(private val bukkitServer: org.bukkit.Server, private val bukkitPlugin: Plugin) {
    fun create(): Server {
        val server = Server(
            Implementation(
                name = "Minecraft Server MCP",
                version = "1.0.0"
            ),
            ServerOptions(
                capabilities = ServerCapabilities(
                    tools = ServerCapabilities.Tools(listChanged = true),
                )
            )
        )

        server.addTool(
            name = "send_command",
            description = """
                Send command as console to a minecraft server
            """.trimIndent(),
            inputSchema = Tool.Input(
                properties = buildJsonObject {
                    putJsonObject("command") {
                        put("type", "string")
                        put("description", "Command to be executed")
                    }
                }
            ),
            handler = { request -> runCommandAsConsole(request, bukkitServer, bukkitPlugin) }
        )

        server.addTool(
            name = "get_logs",
            description = """
                Read latest server logs
            """.trimIndent(),
            handler = { request -> readServerLog(request, bukkitServer.worldContainer.absolutePath) }
        )

        return server
    }
}

