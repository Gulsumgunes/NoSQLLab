// src/main/java/app/Main.java

package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.model.Student;
import app.store.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting application...");

        // Veritabanlarını başlat
        System.out.println("Initializing stores...");
        RedisStore.init();
        HazelcastStore.init();
        MongoStore.init();
        System.out.println("All stores initialized.");

        // Sunucuyu başlat
        port(8080);
        Gson gson = new Gson();

        // --- YENİ VE ÇALIŞAN ENDPOINT TANIMLARI ---

        // Redis
        // URL: http://localhost:8080/redis/student/2025000001
        get("/redis/student/:id", (req, res) -> {
            res.type("application/json");
            Student student = RedisStore.get(req.params(":id"));
            if (student == null) {
                res.status(404);
                return "{\"error\":\"Student not found\"}";
            }
            return gson.toJson(student);
        });

        // Hazelcast
        // URL: http://localhost:8080/hazelcast/student/2025000001
        get("/hazelcast/student/:id", (req, res) -> {
            res.type("application/json");
            Student student = HazelcastStore.get(req.params(":id"));
            if (student == null) {
                res.status(404);
                return "{\"error\":\"Student not found\"}";
            }
            return gson.toJson(student);
        });

        // MongoDB
        // URL: http://localhost:8080/mongo/student/2025000001
        get("/mongo/student/:id", (req, res) -> {
            res.type("application/json");
            Student student = MongoStore.get(req.params(":id"));
            if (student == null) {
                res.status(404);
                return "{\"error\":\"Student not found\"}";
            }
            return gson.toJson(student);
        });

        System.out.println("Server started successfully at http://localhost:8080");
        System.out.println("Ready for testing with new URLs.");
    }
}