import java.text.SimpleDateFormat
import java.util.Locale
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
        terminate_status : String,
        txtDir: File = File("./log"),
    ): String {
        // snapshot
        val snapshot: List<LogEntry> = buf.toList()


        if (!txtDir.exists()) txtDir.mkdirs()

        val now = Date()
        val timeFormatter = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val formattedTime = timeFormatter.format(now)

        val fname = "chatlog_${formattedTime}_${terminate_status}.txt"
        val outFile = File(txtDir, fname)
        val logText = buildString {
            snapshot.forEach { e ->
                appendLine("[${e.ts}] @${e.sender}: ${e.text}")
            }
        }
        if (MongoDBURL != ""){
            val doc = Document()
                .append("time_stamp", now)
                .append("lines", snapshot.size)
                .append("text", logText)
                .append("terminate_status", terminate_status)
            MongoLogStore.col.insertOne(doc)
        }

        val txtText = buildString {
            appendLine("==== Chat Session Log ====")
            appendLine("Timestamp : ${Date()}")
            appendLine("Terminate Status : $terminate_status")
            appendLine("Lines Logged : ${snapshot.size}")
            appendLine("---------------------------")
            appendLine(logText)
        }
        outFile.writeText(logText)

        buf.clear()

        return outFile.absolutePath
    }

    fun clear(){
        buf.clear()
    }
}

