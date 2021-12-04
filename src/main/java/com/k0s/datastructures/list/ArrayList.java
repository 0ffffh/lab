package com.k0s.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayList<T> extends AbstractList<T>{
    private static final int INITIAL_CAPACITY = 5;

    private T[] array;


    public ArrayList() {
        this(INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int initialCapacity) {
        this.array = (T[]) new Object[initialCapacity];
    }


    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        ensureCapacity();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    @Override
    public T remove(int index) {
        T value = get(index);
        removeElement(index);
        return value;

    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return array[index];
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        T originalObject = get(index);
        array[index] = value;
        return originalObject;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size(); i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(T value) {
       if(value == null) {
           for (int index = 0; index < size; index++) {
               if (array[index] == null) {
                   return index;
               }
           }
       } else {
               for (int index = 0; index < size; index++) {
                   if (value.equals(array[index])) {
                       return index;
                   }
               }
        }
        return -1;
    }


    @Override
    public int lastIndexOf(T value) {
        if(value == null) {
            for (int index = size - 1; index >= 0; index--) {
                if (array[index] == null) {
                    return index;
                }
            }
        } else  {
            for (int index = size - 1; index >= 0; index--) {
                if (value.equals(array[index])) {
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator<>();
    }

    private class ArrayListIterator<T> implements Iterator<T> {

        private int index = 0;
        private boolean removable = false;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T value = (T) array[index];
            index++;
            removable = true;
            return value;
        }

        @Override
        public void remove() {
            if(!removable) {
                throw new IllegalStateException();
            }
            index--;
            ArrayList.this.removeElement(index);
            removable = false;

        }
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == array.length) {
            T[] tempArray = (T[]) new Object[(int) (size*1.5)+1];
            System.arraycopy(array, 0, tempArray, 0, size);
            array = tempArray;
        }
    }

    private void removeElement(int index) {
        System.arraycopy(array, index + 1, array, index, size - index-1);
        size--;
        array[size] = null;
    }


}
