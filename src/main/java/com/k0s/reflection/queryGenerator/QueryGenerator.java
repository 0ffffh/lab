package com.k0s.reflection.queryGenerator;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.StringJoiner;

public class QueryGenerator {

    public String createTable(Class<?> clazz){

        String tableName = getTable(clazz);

        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
        stringBuilder.append(tableName).append(" (");

        StringJoiner result = new StringJoiner(", ");
        String prime = "";
        for(Field field : clazz.getDeclaredFields()){
            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                String colName;
                if (column.name().isEmpty()) {
                    colName = field.getName();
                }
                else {
                    colName = column.name();
                }
                colName = colName + " " + column.type();

                if (!column.allowNull()){
                    colName = colName + " NOT NULL";
                }
                if (column.primaryKey()){
                    prime = column.name();
                }
                result.add(colName);
            }
        }
        stringBuilder.append(result)
                .append(", PRIMARY KEY (")
                .append(prime)
                .append("))");

        return stringBuilder.toString();
    }


    public String getAll(Class<?> clazz){

        String tableName = getTable(clazz);

        StringJoiner result = new StringJoiner(", ", "SELECT ", " FROM " + tableName + ";");

        for(Field field : clazz.getDeclaredFields()){
            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                String colName;
                if (column.name().isEmpty()) {
                    colName = field.getName();
                }
                else {
                    colName = column.name();
                }
                result.add(colName);
            }
        }
        return result.toString();
    }

    public String getById(Class<?> clazz, int id){

        String tableName = getTable(clazz);
        String colName;

        StringJoiner result = new StringJoiner(", ");

        for(Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (!column.name().equals("id")) {
                    if (column.name().isEmpty()) {
                        colName = field.getName();
                    }
                    else {
                        colName = column.name();
                    }
                    result.add(colName);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        stringBuilder.append(result)
                .append(" FROM ").append(tableName)
                .append(" WHERE id=").append(id).append(";");

        return stringBuilder.toString();
    }

    public String delete(Class<?> clazz, int id){

        String tableName = getTable(clazz);

        String colName = "";

        for(Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                if (column.name().equals("id")) {
                    colName = column.name();
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder("DELETE FROM ");
        stringBuilder.append(tableName).append(" WHERE ")
                .append(colName).append("=").append(id).append(";");
        return stringBuilder.toString();
    }


    public String insert(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        String tableName = getTable(clazz);

        StringBuilder stringBuilder = new StringBuilder("INSERT INTO " + tableName + " (");
        StringJoiner fieldNames = new StringJoiner(", ");
        StringJoiner fieldValues = new StringJoiner(", ");

        for(Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                String colName;
                if (column.name().isEmpty()) {
                    colName = field.getName();
                }
                else {
                    colName = column.name();
                }
                field.setAccessible(true);
                fieldNames.add(colName);
                fieldValues.add("\"" + field.get(object).toString() + "\"");
            }
        }
        stringBuilder.append(fieldNames)
                .append(") VALUES (")
                .append(fieldValues)
                .append(");");

        return stringBuilder.toString();
    }

    public String update(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        String tableName = getTable(clazz);

        StringBuilder stringBuilder = new StringBuilder("UPDATE " + tableName + " SET ");
        StringJoiner fieldNames = new StringJoiner(", ");

        int id = 0;
        for(Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                String colName;
                if (column.name().isEmpty()) {
                    colName = field.getName();
                }
                else {
                    colName = column.name();
                }
                field.setAccessible(true);
                if(colName.equals("id")){
                    id = field.getInt(object);
                } else
                fieldNames.add(colName + "=" + "\"" + field.get(object) + "\"");
            }
        }
        stringBuilder.append(fieldNames)
                .append(" WHERE id=")
                .append(id);
        return stringBuilder.toString();
    }


    private String getTable(Class<?> clazz){
        Table table = clazz.getDeclaredAnnotation(Table.class);
        if(table == null){
            throw new IllegalArgumentException("@Table missing");
        }

        if (table.name().isEmpty()) {
            return clazz.getName().toUpperCase(Locale.ROOT);
        }
        else return table.name();
    }
}
