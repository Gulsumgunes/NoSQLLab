package app.store;

import com.mongodb.client.*;
import org.bson.Document;
import app.model.Student;
import com.google.gson.Gson;

public class MongoStore {
    private static MongoClient client;
    private static MongoCollection<Document> collection;
    private static Gson gson = new Gson();

    public static void init() {
        client = MongoClients.create("mongodb://localhost:27017");
        collection = client.getDatabase("nosqllab").getCollection("ogrenciler");

        collection.drop();  // Eğer istersen eski veriyi temizle

        for (int i = 0; i < 10000; i++) {
            String id = "2025" + String.format("%06d", i);
            Student s = new Student(id, "Ad Soyad " + i, "Bilgisayar");
            Document doc = Document.parse(gson.toJson(s));
            collection.insertOne(doc);
        }
    }

    public static Student get(String id) {
        Document doc = collection.find(new Document("ogrenciNo", id)).first();
        if (doc == null) return null;
        return gson.fromJson(doc.toJson(), Student.class);
    }
}