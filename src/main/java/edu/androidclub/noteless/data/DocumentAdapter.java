package edu.androidclub.noteless.data;

import org.bson.Document;

import java.lang.reflect.Field;

/**
 * Created by eltgm on 30.03.17.
 */
public class DocumentAdapter{
   public static <T> Document createDocument(T arg) throws IllegalAccessException {
        Document document = new Document();
        Class<?> clazz = arg.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            document.append(field.getName(),field.get(arg));
        }
        return document;
    }
    public static <T> T documentReverse(Class<T> clazz,Document document){
        Object result = new Object();
        Field[] classFields = clazz.getDeclaredFields();
        for (Field classField : classFields) {
            String fieldName = classField.getName();
            if(document.containsKey(fieldName)){
                try {
                    classField.set(result, document.get(fieldName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T) result;
    }
}
