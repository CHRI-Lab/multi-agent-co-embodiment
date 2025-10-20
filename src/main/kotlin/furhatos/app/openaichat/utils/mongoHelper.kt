import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import org.bson.Document
import java.util.Date
val MongoDBURL = System.getenv("MONGO_DB") ?: ""

object MongoLogStore {

    private val uri = MongoDBURL.trim()

    init {
        
        require(uri.isNotEmpty() && uri.startsWith("mongodb")) {
            "MONGODB_URI (or MONGO_DB) not set to a valid MongoDB URI"
        }
    }

    private val client = MongoClients.create(uri)
    private val db = client.getDatabase("chatdb")
    val col: MongoCollection<Document> = db.getCollection("chat_logs")

}
