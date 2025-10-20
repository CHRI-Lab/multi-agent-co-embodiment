import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import org.bson.Document
import java.util.Date
val MongoDBURL = System.getenv("MONGO_DB") ?: ""

object MongoLogStore {

    private val client = MongoClients.create(MongoDBURL)
    private val db = client.getDatabase("chatdb")
    val col: MongoCollection<Document> = db.getCollection("chat_logs")

}
