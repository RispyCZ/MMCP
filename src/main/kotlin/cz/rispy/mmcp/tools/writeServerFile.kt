package cz.rispy.mmcp.tools

import cz.rispy.mmcp.utils.FileUtils
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.json.jsonPrimitive

fun writeServerFile(request: CallToolRequest): CallToolResult {
    val filePath = request.arguments["file_path"]?.jsonPrimitive?.content ?: return CallToolResult(
        content = listOf(TextContent("The 'file_path' parameter is required."))
    )

    val fileContent = request.arguments["file_content"]?.jsonPrimitive?.content ?: return CallToolResult(
        content = listOf(TextContent("The 'file_content' parameter is required."))
    )

    if (!SystemFileSystem.exists(Path(filePath))) return CallToolResult(
        content = listOf(TextContent("File $filePath was not found."))
    )

    FileUtils.writeStringIntoFile(Path(filePath), fileContent)

    return CallToolResult(
        content = listOf(TextContent("Content was written into file $filePath"))
    )
}