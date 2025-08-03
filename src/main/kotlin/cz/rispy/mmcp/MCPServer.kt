package cz.rispy.mmcp

import cz.rispy.mmcp.tools.*
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
            name = "read_file",
            description = """
                Read server file
            """.trimIndent(),
            inputSchema = Tool.Input(
                properties = buildJsonObject {
                    putJsonObject("file_path") {
                        put("type", "string")
                        put("description", "File path")
                    }
                    putJsonObject("lines") {
                        put("type", "string")
                        put("description", "Maximum of lines to be read")
                    }
                }
            ),
            handler = { request -> readServerFile(request) }
        )

        server.addTool(
            name = "write_file",
            description = """
                Write server file
            """.trimIndent(),
            inputSchema = Tool.Input(
                properties = buildJsonObject {
                    putJsonObject("file_path") {
                        put("type", "string")
                        put("description", "File path")
                    }
                    putJsonObject("file_content") {
                        put("type", "string")
                        put("description", "Content to write")
                    }
                }
            ),
            handler = { request -> readServerFile(request) }
        )

        server.addTool(
            name = "list_directory",
            description = """
                List server directory
            """.trimIndent(),
            inputSchema = Tool.Input(
                properties = buildJsonObject {
                    putJsonObject("directory_path") {
                        put("type", "string")
                        put("description", "Directory path")
                    }
                }
            ),
            handler = { request -> listDirectory(request) }
        )

        server.addTool(
            name = "list_root_directory",
            description = """
                List server root directory
            """.trimIndent(),
            handler = { request -> listRootDirectory(request) }
        )

        return server
    }
}

