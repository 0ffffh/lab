package com.k0s.datastructures;

import com.k0s.datastructures.list.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Iterator;
import java.util.NoSuchElementException;


public abstract class AbstractListTest {

    abstract List getList();


    List<Integer> listInteger;
    List<String> listString;
    List<Object> listObject;
    List<Object> listWithFiveElements;
    List<String> list;


    @BeforeEach
    public void before() {

        listInteger = getList();
        listString = getList();
        listObject = getList();
        list = getList();



        listWithFiveElements = getList();
        for (int i = 0; i < 5; i++) {
            listWithFiveElements.add(i);
        }



        list.add("A");
        list.add("B");
        list.add("A");
        list.add("D");

        listString.add("A");
        listString.add("B");
        listString.add(null);
        listString.add("D");

        listObject.add("A");
        listObject.add(null);
        listObject.add(123456789);
        listObject.add(-4564564);

        listInteger.add(5);
        listInteger.add(5342343);
        listInteger.add(-1244324);
        listInteger.add(null);




    }


    @Test
    public void addTest() {
        listString.add("A");
        listString.add("B");
        listString.add(null);
        listString.add("D");

        assertEquals("A", listString.get(0));
        assertEquals("B", listString.get(1));
        assertNull(listString.get(2));
        assertEquals("D", listString.get(3));



        listObject.add("A");
        listObject.add(null);
        listObject.add(123456789);
        listObject.add(-4564564);

        assertEquals("A", listObject.get(0));
        assertNull(listObject.get(1));
        assertEquals(123456789, listObject.get(2));
        assertEquals(-4564564, listObject.get(3));


        listInteger.add(5);
        listInteger.add(5342343);
        listInteger.add(-1244324);
        listInteger.add(null);

        assertEquals(Integer.valueOf(5), listInteger.get(0));
        assertEquals(Integer.valueOf(5342343), listInteger.get(1));
        assertEquals(Integer.valueOf(-1244324), listInteger.get(2));
        assertNull(listInteger.get(3));


    }

    @Test
    public void addByFirstIndexTest() {

        //add first
        listString.add("F", 0);
        assertEquals("F", listString.get(0));
        listString.add("G", 0);
        assertEquals("G", listString.get(0));

        listInteger.add(10, 0);
        assertEquals(Integer.valueOf(10), listInteger.get(0));
        listInteger.add(-153, 0);
        assertEquals(Integer.valueOf(-153), listInteger.get(0));

    }

    @Test
    public void addByLastIndexTest() {

        listString.add("N", listString.size());
        assertEquals("N", listString.get(listString.size() - 1));
        listString.add("M", listString.size());
        assertEquals("M", listString.get(listString.size() - 1));

        listInteger.add(-1000, listInteger.size());
        assertEquals(Integer.valueOf(-1000), listInteger.get(listInteger.size() - 1));
        listInteger.add(1000, listInteger.size());
        assertEquals(Integer.valueOf(1000), listInteger.get(listInteger.size() - 1));
    }

    @Test
    public void addByIndexTest() {
        listString.add("T", 2);
        assertEquals("T", listString.get(2));

        listString.add("T", 3);
        assertEquals("T", listString.get(3));

        listInteger.add(null, 3);
        assertNull(listInteger.get(3));

        listInteger.add(125, 3);
        assertEquals(Integer.valueOf(125), listInteger.get(3));
    }

    @Test
    public void addByOutOfRangeIndexTest() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            //exception
            listString.add("N", listString.size() + 5);
            listInteger.add(100, listInteger.size() + 5);
        });
    }

    @Test
    public void removeByIndexFirstTest()

    {
        String actualString = listString.remove(0);
        assertEquals("A", actualString);

        int actualInteger = listInteger.remove(0);
        assertEquals(Integer.valueOf(5), actualInteger);

        for (int i = 0; i < 5; i++) {
            assertEquals(Integer.valueOf(i),listWithFiveElements.remove(0));
        }

    }
    @Test
    public void removeByIndexLastTest()

    {
        String actualString = listString.remove(3);
        assertEquals("D", actualString);


        assertNull(listInteger.remove(3));

        for (int i = listWithFiveElements.size()-1; i > 0; i--) {
            assertEquals(Integer.valueOf(i),listWithFiveElements.remove(i));
        }

    }

    @Test
    public void removeByIndexTest()

    { //[A B null D]

        assertEquals("D", listString.remove(3));
        assertEquals("B", listString.remove(1));

    }

    @Test
    public void removeByIndexExceptionTest()
    { //[A B null D]
        assertThrows(IndexOutOfBoundsException.class, () -> {
            //exception
            for (int i = 0; i < 10; i++) {
                assertEquals(i,listWithFiveElements.remove(0));
            }
        });



    }
    @Test
    public void removeByObjectTest()

    { //[A B null D]
        assertTrue(listString.remove("D"));
        assertTrue(listString.remove(null));
        assertFalse(listString.remove("K"));

    }

    @Test
    public void setGetTest()

    {   //[A B null D]
        //  5  5342343 -1244324 null

        listInteger.set(100, 0);
        assertEquals(Integer.valueOf(100), listInteger.get(0));

        listInteger.set(null, 1);
        assertNull(listInteger.get(1));

        listInteger.set(100, 2);
        assertEquals(Integer.valueOf(100), listInteger.get(2));

        listInteger.set(100, 3);
        assertEquals(Integer.valueOf(100), listInteger.get(3));



        listString.set("T", 0);
        assertEquals("T", listString.get(0));

        listString.set(null, 1);
        assertNull(listString.get(1));

        listString.set("T", 2);
        assertEquals("T", listString.get(2));

        listString.set("T", 3);
        assertEquals("T", listString.get(3));



    }

    @Test
    public void sizeTest() {   //[A B null D]
        //  5  5342343 -1244324 null
        assertEquals(4, listString.size());
        assertEquals(5, listWithFiveElements.size());

        listString.add("T");
        assertEquals(5, listString.size());

        listWithFiveElements.remove(0);
        assertEquals(4, listWithFiveElements.size());

    }
    @Test
    public void clearEmptyTest() {   //[A B null D]
        //  5  5342343 -1244324 null
        assertEquals(4, listString.size());
        listString.clear();
        assertEquals(0, listString.size());
        assertTrue(listString.isEmpty());


        listWithFiveElements.remove(0);
        assertEquals(4, listWithFiveElements.size());

        listWithFiveElements.clear();
        assertEquals(0, listWithFiveElements.size());
        assertTrue(listWithFiveElements.isEmpty());

        listWithFiveElements.add("A");
        assertFalse(listWithFiveElements.isEmpty());

    }

    @Test
    public void containsTest(){
        //[A B null D]
        //  5  5342343 -1244324 null
        assertTrue(listString.contains(null));
        assertTrue(listString.contains("A"));
        assertTrue(listString.contains("D"));
        assertFalse(listString.contains("K"));

        assertTrue(listInteger.contains(5));
        assertTrue(listInteger.contains(null));
        assertTrue(listInteger.contains(-1244324));
        assertFalse(listInteger.contains(29));
    }

    @Test
    public void indexOfTest(){
        //[A B null D]
        //  5  5342343 -1244324 null
        assertEquals(2, listString.indexOf(null));
        assertEquals(0, listString.indexOf("A"));
        assertEquals(-1,listString.indexOf("M"));

        assertEquals(0, listInteger.indexOf(5));
        assertEquals(3, listInteger.indexOf(null));
        assertEquals(-1, listInteger.indexOf(555));

        for (int i = 0; i < 5; i++) {
            assertEquals(i,listWithFiveElements.indexOf(i));
        }

    }

    @Test
    public void lastIndexOfTest(){
        //[A B null D]
        //  5  5342343 -1244324 null
        assertEquals(2, listString.lastIndexOf(null));
        assertEquals(0, listString.lastIndexOf("A"));
        assertEquals(-1,listString.lastIndexOf("M"));

        listString.add("A");
        assertEquals(4, listString.lastIndexOf("A"));

        assertEquals(0, listInteger.lastIndexOf(5));
        assertEquals(3, listInteger.lastIndexOf(null));
        assertEquals(-1, listInteger.lastIndexOf(555));

        listInteger.add(5,2);
        assertEquals(2, listInteger.lastIndexOf(5));

        for (int i = 0; i < 5; i++) {
            assertEquals(i,listWithFiveElements.lastIndexOf(i));
        }

    }
    @Test
    public void toStringTest() {
        while (listWithFiveElements.size() > 0) {
            System.out.println(listWithFiveElements.toString());
            listWithFiveElements.remove(0);
        }
        System.out.println(listWithFiveElements.toString());
        assertEquals("[]",listWithFiveElements.toString());
    }

    @Test
    public void testIteratorRemove() {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.equals("A")) {
                iterator.remove();
            }
        }
        assertFalse(list.contains("A"));
    }

    @Test
    public void testIteratorRemoveException() {
        assertThrows(IllegalStateException.class, () -> {
            //exception
            Iterator<String> iterator = list.iterator();
            iterator.remove();
        });

    }

    @Test
    public void testIteratorNextException() {
        assertThrows(NoSuchElementException.class, () -> {
            //exception
            Iterator<String> iterator = list.iterator();
            for (int i = 0; i < 10; i++) {
                 iterator.next();
            }
        });

    }

}
