package edu.androidclub.noteless;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import edu.androidclub.noteless.data.UsersRepository;
import edu.androidclub.noteless.data.remote.NotesMongoStorage;
import edu.androidclub.noteless.data.remote.UsersList;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class App extends Application {

    private final Set<Class<?>> classes;
    private final Set<Object> singletons;

    private final MongoDatabase notesRepository;
    private final UsersRepository usersRepository;

    public App() {
        this.classes = new HashSet<>();
        this.singletons = new HashSet<>();
        this.classes.add(JacksonJaxbJsonProvider.class);


        MongoClient mongoClient = new MongoClient(DbConfig.DB_HOST, DbConfig.DB_PORT);
        this.notesRepository = mongoClient.getDatabase(DbConfig.DB_NAME_NOTES);

        this.usersRepository = new UsersList(notesRepository.getCollection("users") );

        this.singletons.add(
                new NotesResource(
                        new NotesMongoStorage(
                                notesRepository.getCollection(DbConfig.DB_COLLECTION_NOTES)
                        )
                )
        );
        this.singletons.add(
                new UsersResource(
                        usersRepository
                )
        );
        this.singletons.add(
                new AuthFeature(
                        new AuthFilter(
                                usersRepository
                        )
                )
        );
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    public static class DbConfig {
        public static final String DB_HOST = "localhost";
        public static final int DB_PORT = 27018;
        public static final String DB_NAME_NOTES = "notes_db";
        public static final String DB_COLLECTION_NOTES = "notes";
    }
}
