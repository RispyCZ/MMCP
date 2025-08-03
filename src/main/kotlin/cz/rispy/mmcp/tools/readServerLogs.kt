package cz.rispy.mmcp.tools

import cz.rispy.mmcp.utils.FileUtils
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.io.files.Path
import kotlinx.serialization.json.jsonPrimitive

fun readServerLog(request: CallToolRequest, serverRootPath: String): CallToolResult {
    val maxLines = request.arguments["lines"]?.jsonPrimitive?.content?.toInt() ?: return CallToolResult(
        content = listOf(TextContent("The 'lines' parameter is required."))
    )


    val serverLog = mutableListOf<String>()

    FileUtils.readFirstLinesFromFile(Path("$serverRootPath/logs/latest.log"), maxLines, serverLog)

    return CallToolResult(
        content = listOf(TextContent("Latest server: ${serverLog.joinToString("\n")}"))
    )
}