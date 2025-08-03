package cz.rispy.mmcp.tools

import cz.rispy.mmcp.utils.FileUtils
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.json.jsonPrimitive

fun readServerFile(request: CallToolRequest): CallToolResult {
    val filePath = request.arguments["file_path"]?.jsonPrimitive?.content ?: return CallToolResult(
        content = listOf(TextContent("The 'file_path' parameter is required."))
    )
    val maxLines = request.arguments["lines"]?.jsonPrimitive?.content?.toInt() ?: return CallToolResult(
        content = listOf(TextContent("The 'lines' parameter is required."))
    )
    if (!SystemFileSystem.exists(Path(filePath))) return CallToolResult(
        content = listOf(TextContent("File $filePath was not found."))
    )


    val serverFile = mutableListOf<String>()

    FileUtils.readFirstLinesFromFile(Path(filePath), maxLines, serverFile)

    return CallToolResult(
        content = listOf(TextContent(serverFile.joinToString("\n")))
    )
}