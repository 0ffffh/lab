package com.k0s.datastructures;
import com.k0s.datastructures.list.AbstractList;
import com.k0s.datastructures.list.LinkedList;
import com.k0s.datastructures.list.List;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Iterator;



public class LinkedListTest extends AbstractListTest {

    @Override
    List getList() {
        return new LinkedList();
    }

    @Test
    public void iteratorNextHasNextTest() {
        AbstractList<Object> linkedList = new LinkedList<>();
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("A");
        linkedList.add("D");
        linkedList.add("C");

        for (Object o : linkedList) {
            System.out.println(o);
        }

        Iterator iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next.equals("A") || next.equals("D")) {
                iterator.remove();
            }
        }

        assertFalse(linkedList.contains("A"));
        assertFalse(linkedList.contains("D"));

        System.out.println(linkedList.getClass());


    }

}


