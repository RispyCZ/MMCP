package cz.rispy.mmcp.utils

object AuthUtils {
    fun validateApiToken(
        authorizationHeader: String?,
        configApiToken: String,
    ): Boolean {
        if (authorizationHeader == null) return false
        val apiToken = authorizationHeader.split(" ")[1]
        return configApiToken == apiToken
    }

    fun validateIP(
        whitelistedIps: MutableList<String>,
        remoteAddress: String,
    ): Boolean {
        if (whitelistedIps.isEmpty()) return true
        return remoteAddress in whitelistedIps
    }
}
