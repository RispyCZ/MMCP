import com.typesafe.config.Config
import cz.rispy.mmcp.utils.AuthUtils
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import io.ktor.util.collections.*
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.SseServerTransport


class HTTPTransportServer(
    private val server: Server,
    private val config: Config
) {
    private val sessions = ConcurrentMap<String, SseServerTransport>()

    private val SecurityPlugin = createApplicationPlugin("apiToken") {
        on(AuthenticationChecked) { call ->
            if (
                !AuthUtils.validateIP(
                    config.getStringList("plugin.http.whitelistedIps"),
                    call.request.local.remoteAddress
                ) ||
                !AuthUtils.validateApiToken(
                    call.request.headers["Authorization"],
                    config.getString("plugin.http.apiToken")
                )
            ) {
                call.respondText("Unauthorized", status = HttpStatusCode.Forbidden)
            }
        }
    }


    fun create(): EmbeddedServer<CIOApplicationEngine, CIOApplicationEngine.Configuration> {
        return embeddedServer(
            CIO,
            host = config.getString("plugin.http.address"),
            port = config.getInt("plugin.http.port")
        ) {
            install(SecurityPlugin)
            install(SSE)
            install(Authentication)
            routing {
                sse("/sse") {
                    val transport = SseServerTransport("/message", this)
                    sessions[transport.sessionId] = transport

                    server.onClose {
                        log.info("MCP server was closed.")
                        sessions.remove(transport.sessionId)
                    }
                    server.connect(transport)
                }
                post("/message") {
                    val sessionId = call.request.queryParameters["sessionId"]
                    val transport = sessionId?.let { sessions[it] }
                    if (transport == null) {
                        call.respond(HttpStatusCode.NotFound, "Session not found")
                        return@post
                    }
                    transport.handlePostMessage(call)
                }
            }

        }

    }
}