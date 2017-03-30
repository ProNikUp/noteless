package edu.androidclub.noteless.data;

import org.bson.Document;

import java.lang.reflect.Field;

/**
 * Created by eltgm on 30.03.17.
 */
public class DocumentAdapter<T>{
    Document createDocument(T arg) throws IllegalAccessException {
        Document document = new Document();
        Class<?> clazz = arg.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            document.append(field.getName(),field.get(arg));
        }
        return document;
    }
    T documentReverse(Document document){

        T returned;
        returned = document.get
        return returned;
    }
}
