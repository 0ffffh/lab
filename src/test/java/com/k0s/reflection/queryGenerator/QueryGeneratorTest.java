package com.k0s.reflection.queryGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class QueryGeneratorTest {

    QueryGenerator queryGenerator = new QueryGenerator();

    @Test
    public void createTable(){
        String createTable = queryGenerator.createTable(Person.class);

        String exceptedSql = "CREATE TABLE PERSON (id INT NOT NULL, firstName VARCHAR(255)," +
                " lastName VARCHAR(255), age INT NOT NULL, PRIMARY KEY (id))";

//        System.out.println(createTable);
//        System.out.println(exceptedSql);
        assertEquals(exceptedSql, createTable);

    }


    @Test
    public void getAllTest(){

        String getAllSql = queryGenerator.getAll(Person.class);

        String exceptedSql = "SELECT id, firstName, lastName, age FROM PERSON;";

//        System.out.println(getAllSql);
//        System.out.println(exceptedSql);

        assertEquals(exceptedSql, getAllSql);

    }

    @Test
    public void getById(){

        int id = 0;
        String getById = queryGenerator.getById(Person.class, id);
        String exceptedSql = "SELECT firstName, lastName, age FROM PERSON WHERE id=" + id + ";";

        System.out.println(getById);
        System.out.println(exceptedSql);

        assertEquals(exceptedSql, getById);
    }
    @Test
    public void delete(){

        int id = 0;
        String delete = queryGenerator.delete(Person.class, id);
        String exceptedSql = "DELETE FROM PERSON WHERE id=" + id + ";";

//        System.out.println(delete);
//        System.out.println(exceptedSql);

        assertEquals(exceptedSql, delete);
    }


    @Test
    public void insert() throws IllegalAccessException {
        Person person = new Person(1, "Ivan", "Ivanov", 30);

        String insert = queryGenerator.insert(person);

        String exceptedSql = "INSERT INTO PERSON (id, firstName, lastName, age) VALUES (\"" +
                person.getId() + "\", " +
                "\"" + person.getFirstName() + "\", \"" +
                person.getLastName() + "\", \"" +
                person.getAge() + "\");";

//        System.out.println(insert);
//        System.out.println(exceptedSql);

        assertEquals(exceptedSql, insert);
    }


    @Test
    public void update() throws IllegalAccessException {
        Person person = new Person(1, "Ivan", "Ivanov", 30);

        String update = queryGenerator.update(person);

        String exceptedSql = "UPDATE PERSON SET firstName=\"" + person.getFirstName() +
                "\", lastName=\"" + person.getLastName() +
                "\", age=\"" + person.getAge() +
                "\" WHERE id=" + person.getId();

//        System.out.println(update);
//        System.out.println(exceptedSql);

        assertEquals(exceptedSql, update);

    }
}
