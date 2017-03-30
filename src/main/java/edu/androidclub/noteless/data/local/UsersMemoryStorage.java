package edu.androidclub.noteless.data.local;

import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.domain.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by senik11 on 23.03.17.
 */
public class UsersMemoryStorage implements UsersRepository {

    private final Map<String, User> database;

    public UsersMemoryStorage() {
        this.database = new ConcurrentHashMap<>();
    }

    @Override
    public User getUserByToken(String token) {
        return database.get(token);
    }

    @Override
    public User createUser(String token, long timestamp) {
        String id = UUID.randomUUID().toString();
        User user = new User(id, token, timestamp);
        database.put(token, user);
        return user;
    }

}
