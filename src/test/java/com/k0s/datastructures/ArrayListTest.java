package com.k0s.datastructures;
import com.k0s.datastructures.list.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import java.util.Iterator;



public class ArrayListTest extends AbstractListTest {


    @Override
    List getList() {
        return new ArrayList<>();
    }

    @Test
    public void iteratorNextHasNextTest() throws IllegalAccessException {
        AbstractList<Object> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("A");
        arrayList.add("D");
        arrayList.add("C");



        for (Object o : arrayList) {
            System.out.println(o);
        }

        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next.equals("A") || next.equals("D")) {
                iterator.remove();
            }
        }

        assertFalse(arrayList.contains("A"));
        assertFalse(arrayList.contains("D"));

        System.out.println(arrayList.getClass());


    }
}
