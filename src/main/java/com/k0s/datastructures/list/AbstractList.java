package com.k0s.datastructures.list;

import java.util.Iterator;
import java.util.StringJoiner;

public abstract class AbstractList<T> implements List<T>{
    protected int size;

    public void add(T value) {
        add(value, size);
    }

    @Override
    public boolean remove(T value) {
        @SuppressWarnings("unchecked")
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next == value) {
                    iterator.remove();
                    return true;
            }

            if (next != null && next.equals(value)) {
                    iterator.remove();
                    return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    void validateIndex(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Invalid index: " + index
                    + ", index should be in range [0 . . " + (size()-1) + "] " );
    }
    void validateIndexForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Invalid index: " + index
                    + ", index should be in range [0 .. " + size() + "]" );
    }

    @Override
    public String toString () {
        StringJoiner result = new StringJoiner(", ", "[", "]");
        for(T value : this) {
            result.add(String.valueOf(value));
        }
        return result.toString();
    }
}
