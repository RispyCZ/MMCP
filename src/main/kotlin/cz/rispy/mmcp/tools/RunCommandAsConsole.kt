package cz.rispy.mmcp.tools

import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.serialization.json.jsonPrimitive
import org.bukkit.Server
import org.bukkit.command.CommandException
import org.bukkit.plugin.Plugin

fun runCommandAsConsole(
    request: CallToolRequest,
    bukkitServer: Server,
    bukkitPlugin: Plugin,
): CallToolResult {
    val command =
        request.arguments["command"]?.jsonPrimitive?.content ?: return CallToolResult(
            content = listOf(TextContent("The 'command' parameter is required.")),
        )

    var commandExecutionResult: Boolean

    try {
        commandExecutionResult =
            bukkitServer.scheduler
                .callSyncMethod(bukkitPlugin) {
                    bukkitServer.dispatchCommand(bukkitServer.consoleSender, command)
                }.get()
    } catch (e: CommandException) {
        return CallToolResult(
            content = listOf(TextContent(e.message)),
        )
    }

    return CallToolResult(
        content = listOf(TextContent("Command execution result: $commandExecutionResult")),
    )
}
