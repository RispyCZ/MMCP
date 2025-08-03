package cz.rispy.mmcp.tools

import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.json.jsonPrimitive

fun listDirectory(request: CallToolRequest): CallToolResult {
    val directoryPath = request.arguments["directory_path"]?.jsonPrimitive?.content ?: return CallToolResult(
        content = listOf(TextContent("The 'directory_path' parameter is required."))
    )

    if (!SystemFileSystem.exists(Path(directoryPath))) return CallToolResult(
        content = listOf(TextContent("Directory $directoryPath was not found."))
    )

    SystemFileSystem.metadataOrNull(Path(directoryPath))?.isDirectory?.let {
        if (!it) return CallToolResult(
            content = listOf(TextContent("$directoryPath isn't directory"))
        )
    }

    return CallToolResult(
        content = SystemFileSystem.list(Path(directoryPath)).map { dir -> TextContent(dir.name) }
    )
}