package app.store;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import app.model.Student;
import com.google.gson.Gson;

public class RedisStore {
    private static JedisPool pool;
    private static final Gson gson = new Gson();

    public static void init() {
        pool = new JedisPool("localhost", 6379);
        try (Jedis jedis = pool.getResource()) {
            for (int i = 0; i < 10000; i++) {
                String id = "2025" + String.format("%06d", i);
                Student s = new Student(id, "Ad Soyad " + i, "Bilgisayar");
                jedis.set(id, gson.toJson(s));
            }
        }
    }

    public static Student get(String id) {
        if (pool == null) throw new IllegalStateException("Redis pool not initialized!");
        try (Jedis jedis = pool.getResource()) {
            String json = jedis.get(id);
            if (json == null) return null;
            return gson.fromJson(json, Student.class);
        }
    }
}

