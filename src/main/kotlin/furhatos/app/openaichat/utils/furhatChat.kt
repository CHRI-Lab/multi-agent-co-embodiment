import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.Furhat
import furhatos.flow.kotlin.furhat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

private val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

private val JSON = "application/json; charset=utf-8".toMediaType()

private val baseUrl = "http://127.0.0.1:5000"
private val http = OkHttpClient.Builder()
    .connectTimeout(5, TimeUnit.SECONDS)
    .writeTimeout(5, TimeUnit.SECONDS)
    .readTimeout(0, TimeUnit.SECONDS) // important for SSE (no read timeout)
    .build()

/**
 * POST /api/message  { "role": "user|assistant|system", "content": "..." }
 */
suspend fun postMessage(role: String, content: String, name: String): JSONObject =
    withContext(Dispatchers.IO) {
        val body = JSONObject()
            .put("role", role)
            .put("content", content)
            .put("name", name)
            .toString()
            .toRequestBody(JSON)

        val req = Request.Builder()
            .url("$baseUrl/api/message")
            .post(body)
            .build()

        http.newCall(req).execute().use { response ->
            val body = response.body?.string()
                ?: throw IllegalStateException("Empty response body")
            JSONObject(body)
        }
    }

/**
 * POST /api/clear
 */
suspend fun clearChat(): String =
    withContext(Dispatchers.IO) {
        val req = Request.Builder()
            .url("$baseUrl/api/clear")
            .post("".toRequestBody("text/plain".toMediaType()))
            .build()

        http.newCall(req).execute().use { response ->
            response.body?.string().orEmpty()
        }

    }

fun postMessageAsync(role: String, content: String, name: String) {
    ioScope.launch {
        postMessage(role, content, name)
    }
}

fun clearChatAsync() {
    ioScope.launch {
        clearChat()
    }
}
fun FlowControlRunner.sayAndLog(role: String, text: String, sender: String) {
    postMessageAsync(role, text, sender)
    Logs.append(text, sender)

}
