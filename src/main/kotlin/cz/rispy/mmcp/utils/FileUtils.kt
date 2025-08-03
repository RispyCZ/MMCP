package cz.rispy.mmcp.utils

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readLine
import kotlinx.io.writeString

object FileUtils {
    fun readFirstLinesFromFile(
        path: Path,
        maxLines: Int,
        into: MutableCollection<String>,
    ) {
        if (!SystemFileSystem.exists(path)) return
        SystemFileSystem.source(path).buffered().use { src ->
            generateSequence { src.readLine() }
                .take(maxLines)
                .forEach(into::add)
        }
    }

    fun writeStringIntoFile(
        path: Path,
        string: String,
    ) {
        val sink =
            SystemFileSystem
                .sink(Path(path))
                .buffered()
        try {
            sink.writeString(string)
            sink.flush()
        } finally {
            sink.close()
        }
    }
}
