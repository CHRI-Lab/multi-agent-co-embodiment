import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Date
import org.bson.Document
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

data class LogEntry(
    val ts: String = DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
    val sender: String,
    val text: String
)

object Logs {
    val lock = ReentrantReadWriteLock()
    val buf = mutableListOf<LogEntry>()

    fun append(text: String, sender: String) {
        buf += LogEntry(sender = sender, text = text)
    }

    fun saveLog(
        txtDir: File = File("./log"),
    ): String {
        // snapshot
        val snapshot: List<LogEntry> = buf.toList()


        if (!txtDir.exists()) txtDir.mkdirs()
        val fname = "chatlog_${System.currentTimeMillis()}.txt"
        val outFile = File(txtDir, fname)
        val logText = buildString {
            snapshot.forEach { e ->
                appendLine("[${e.ts}] @${e.sender}: ${e.text}")
            }
        }
        val now = Date() // BSON Date for good TTL/index support
        if (MongoDBURL != ""){
            val doc = Document()
                .append("time_stamp", now)
                .append("lines", snapshot.size)
                .append("text", logText)
            MongoLogStore.col.insertOne(doc)
        }

        outFile.writeText(logText)

        buf.clear()

        return outFile.absolutePath
    }

}

