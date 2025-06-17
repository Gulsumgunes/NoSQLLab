/* // src/main/java/app/store/RedisStore.java

package app.store;

import redis.clients.jedis.Jedis;
import app.model.Student;
import com.google.gson.Gson;

public class RedisStore {
    // static Jedis nesnesini kaldırıyoruz.
    private static Gson gson = new Gson();

    public static void init() {
        // init içinde sadece veri ekleme işlemi yapıyoruz.
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            System.out.println("Populating Redis...");
            for (int i = 0; i < 10000; i++) {
                String id = "2025" + String.format("%06d", i);
                Student s = new Student(id, "Ad Soyad " + i, "Bilgisayar");
                jedis.set(id, gson.toJson(s));
            }
            System.out.println("Redis populated.");
        } // try-with-resources bloğu bittiğinde jedis bağlantısı otomatik olarak kapanır.
    }

    public static Student get(String id) {
        // get metodu her çağrıldığında YENİ bir bağlantı açıp kapatıyoruz.
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            String json = jedis.get(id);
            if (json == null) return null;
            return gson.fromJson(json, Student.class);
        } // Bağlantı burada yine otomatik olarak kapanır.
    }
} */


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

