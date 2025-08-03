package cz.rispy.mmcp.tools

import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.TextContent
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

const val ROOT_DIRECTORY_PATH = "."

fun listRootDirectory(request: CallToolRequest): CallToolResult {
    return CallToolResult(
        content = SystemFileSystem.list(Path(ROOT_DIRECTORY_PATH)).map { dir -> TextContent(dir.name) }
    )
}