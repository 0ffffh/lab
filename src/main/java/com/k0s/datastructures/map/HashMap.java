package com.k0s.datastructures.map;


import java.util.*;

public class HashMap <K,V> implements Map<K,V> {

    private static final int INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOADFACTOR = 0.75f;
    private int size;
    private Node<K,V>[] table;


    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashMap(int size){
        this.table = new Node[size];
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
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public V get(K key) {
        Node<K,V> current = getNode(key);
        return current == null ? null : current.value;
    }

    /**For HashMap, it allows one null key and there is a null check for keys,
     *  if the key is null then that element will be stored in a zero location in Entry array.
     */
    @Override
    public V put(K key, V value) {
        if(size > table.length*DEFAULT_LOADFACTOR){
            resize(table.length*2);
        }
        int hash = generateHash(key);
        int index = generateIndex(hash, table.length);

        for(Node<K,V> current = table[index]; current != null; current = current.next){
            if(current.hash == hash && Objects.equals(current.key,key)){

                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            if(current.next == null){
                current.next = new Node<>(key, value, hash);
                size++;
                return null;
            }
        }
        table[index] = new Node<>(key, value, hash);
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        if(size == 0) {
            return null;
        }
        int hash = generateHash(key);
        int index = generateIndex(hash, table.length);

        Node<K,V> prev = table[index];
        Node<K,V> nodeToRemove = prev;

        while (nodeToRemove != null) {
            Node<K,V> next = nodeToRemove.next;
            if(nodeToRemove.hash == hash && Objects.equals(nodeToRemove.key,key)){

                size--;
                V removedValue = nodeToRemove.value;
                if (prev == nodeToRemove){
                    table[index] = next;
                }
                else {
                    prev.next = next;
                }
                return removedValue;
            }
            prev = nodeToRemove;
            nodeToRemove = next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        Node<K,V> current = getNode(key);
        return current != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (Node<K, V> node : table) {
            while (node != null){
                if(node.getValue() == value){
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public String toString () {
        StringJoiner result = new StringJoiner(", ", "{", "}");
        for (Node<K, V> node : table) {
            while (node != null){
                result.add(node.getKey() + " = " + node.getValue());
                node = node.next;
            }
        }
        return result.toString();
    }

    @Override
    public Iterator<Entry<K,V>> iterator() {
        return new  MapIterator();
    }


    private  class MapIterator implements Iterator<Entry<K, V>> {

        Node<K,V> current;
        Node<K,V> next;
        int index;
        int iteratorSize = size;

        MapIterator(){
            current = next = null;
            index = 0;
            if(size > 0){
                while(next == null && index<table.length){
                    next = table[index];
                    index++;
                }
            }
        }


        @Override
        public boolean hasNext() {
            return next != null;

        }

        @Override
        public Node<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in  HashMap");
            }
            if(iteratorSize != size){
                throw new ConcurrentModificationException("HashMap has been modified outside of the iterator");
            }

            current = next;
            next = current.next;
            while(next == null && index<table.length){
                next = table[index];
                index++;
            }
            return current;
        }

        @Override
        public void remove() {
            if(current == null){
                throw new IllegalStateException(" next() method has not yet been called, or the remove() method" +
                        " has already been called after the last call to the next() method");
            }
            if(iteratorSize != size){
                throw new ConcurrentModificationException("HashMap has been modified outside of the iterator");
            }

            HashMap.this.remove(current.key);
            iteratorSize--;
            current = null;
        }
    }


    private final static class Node<K,V> implements Map.Entry<K, V>{
        private final K key;
        private V value;
        private Node<K,V> next;
        private final int hash;


        Node(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }


    private int generateHash(K key){
        return (key == null) ? 0 : key.hashCode();
    }
    private int generateIndex(int hash, int length){
        return Math.abs(hash%length);
    }


    private Node<K, V> getNode(K key) {
        if (size == 0) {
            return null;
        }
        int hash = generateHash(key);
        for(Node<K,V> current = table[generateIndex(hash, table.length)]; current != null; current = current.next){
            if(current.hash == hash && Objects.equals(current.key,key)){
                return current;
            }
        }
        return null;
    }

    void resize(int newCapacity) {
        @SuppressWarnings("unchecked")
        Node<K,V>[] newTable = new Node[newCapacity];
        int count = 0;
        for (Node<K, V> node : table) {
            while (node != null){
                int index = generateIndex(node.hash, newCapacity);
                if (newTable[index] == null){
                    newTable[index] = new Node<>(node.key, node.value, node.hash);
                    count++;
                } else {
                    for(Node<K,V> current = newTable[index]; current != null; current = current.next){
                        if(current.next == null){
                            current.next = new Node<>(node.key, node.value, node.hash);
                            count++;
                            break;
                        }
                    }
                }
                node = node.next;
            }
        }
        if (size == count){
            this.clear();
            size = count;
            table = newTable;
        } else {
            throw new RuntimeException("HashMap resize crash, size = " + size + " size after resize = " + count + " data lost");
        }

    }
}