package ru.job4j.ref;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        User user = users.get(id);
        return user != null ? User.of(user.getName()) : null;
    }

    public List<User> findAll() {
         List<User> list = new ArrayList<>();
         for (int a : users.keySet()) {
             list.add(User.of(users.get(a).getName()));
         }
         return list;
    }
}
