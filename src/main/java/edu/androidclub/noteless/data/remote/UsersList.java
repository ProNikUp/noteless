package edu.androidclub.noteless.data.remote;

import com.mongodb.DBCollection;
import com.mongodb.Function;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.androidclub.noteless.App;
import edu.androidclub.noteless.data.DocumentAdapter;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.domain.User;

import javax.swing.text.Document;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by eltgm on 30.03.17.
 */
public class UsersList implements UsersRepository {

    private final MongoCollection<org.bson.Document> usersCollection;

    public UsersList(MongoCollection<org.bson.Document> usersCollection) {
        MongoClient mongoClient = new MongoClient(App.DbConfig.DB_HOST, App.DbConfig.DB_PORT);
        this.usersCollection = usersCollection;
    }

    @Override
    public User getUserByToken(String token) {
        return usersCollection.find(eq("token",token))
                .map(new Function<org.bson.Document, User>() {
                    @Override
                    public User apply(org.bson.Document document) {
                        if(document == null){
                            return null;
                        }
                        return DocumentAdapter.documentReverse(User.class,document);
                    }
                })
                .first();
    }

    @Override
    public User createUser(String token, long timestamp) {
        MongoCollection<org.bson.Document> dbCollection = usersCollection;

        org.bson.Document document = new org.bson.Document();

        String id = UUID.randomUUID().toString();
        User user = new User(id, token, timestamp);

        document.append("id",id).append("token",token).append("timestamp",timestamp);
        dbCollection.insertOne(document);

        return user;
    }
}
