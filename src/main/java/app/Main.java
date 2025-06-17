// src/main/java/app/Main.java

package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import app.model.Student;
import app.store.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting application...");


        System.out.println("Initializing stores...");
        RedisStore.init();
        HazelcastStore.init();
        MongoStore.init();
        System.out.println("All stores initialized.");


        port(8080);
        Gson gson = new Gson();



        // Redis

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