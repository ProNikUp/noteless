package edu.androidclub.noteless.data.remote;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.androidclub.noteless.App;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.domain.User;

import javax.swing.text.Document;
import java.util.UUID;

/**
 * Created by eltgm on 30.03.17.
 */
public class UsersList implements UsersRepository {

    private final MongoDatabase database;

    public UsersList() {
        MongoClient mongoClient = new MongoClient(App.DbConfig.DB_HOST, App.DbConfig.DB_PORT);
        this.database = mongoClient.getDatabase(App.DbConfig.DB_NAME_NOTES);
    }

    @Override
    public User getUserByToken(String token) {
        return database.getCollection("notes"); //как вытащить из коллекции?
    }

    @Override
    public User createUser(String token, long timestamp) {
        MongoCollection<org.bson.Document> dbCollection = database.getCollection("notes");

        org.bson.Document document = new org.bson.Document();

        String id = UUID.randomUUID().toString();
        User user = new User(id, token, timestamp);

        document.append("id",id).append("token",token).append("timestamp",timestamp);
        dbCollection.insertOne(document);

        return user;
    }
}
