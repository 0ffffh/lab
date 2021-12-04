package com.k0s.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> extends AbstractList<T> {

    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value, int index) {
        validateIndexForAdd(index);
        Node<T> newNode = new Node<>(value);
        if (size == 0) {
            head = tail = newNode;
        } else if (index == 0) {
            addFirst(newNode);
        } else if (index == size()) {
            addLast(newNode);

        } else {
            Node<T> indexNode = getNode(index);

            newNode.next = indexNode;
            newNode.prev = indexNode.prev;
            newNode.prev.next = newNode;
            indexNode.prev = newNode;
        }
        size++;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        removeNode(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> originalNode = getNode(index);
        T originalValue = originalNode.value;
        originalNode.value = value;
        return originalValue;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public int indexOf(T value) {
        Node<T> indexNode = head;
        if(value == null) {
            for (int i = 0; i < size(); i++) {
                if (indexNode.value == null) {
                    return i;
                }
                indexNode = indexNode.next;
            }

        } else {
            for (int i = 0; i < size(); i++) {
                    if (value.equals(indexNode.value)) {
                        return i;
                    }
                    indexNode = indexNode.next;
                }
            }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        Node<T> indexNode = tail;
        if(value == null) {
            for (int i = size-1; i >= 0; i--) {

                if (indexNode.value == null) {
                    return i;
                }
                indexNode = indexNode.prev;
            }
        } else {
            for (int i = size-1; i >= 0; i--){
                if (value.equals(indexNode.value)) {
                    return i;
                }
                indexNode = indexNode.prev;

            }
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {

        private Node<T> current = head;
        private Node<T> tempCurrent;
        private boolean removable = false;


        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T value = current.value;
            tempCurrent = current;
            current = current.next;
            removable = true;
            return value;
        }

        @Override
        public void remove() {
            if(!removable) {
                throw new IllegalStateException();
            }
            LinkedList.this.removeNode(tempCurrent);
            removable = false;
        }

    }


    private static class Node<T> {

        private Node<T> next;
        private Node<T> prev;
        private T value;

        private Node(T value) {
            this.value = value;
        }
    }

    private Node<T> getNode(int index) {
        validateIndex(index);
        if (index <= size/2) {
            Node<T> indexNode = head;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
            return indexNode;
        } else {
            Node<T> indexNode = tail;
            for (int i = size()-1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        return indexNode;
        }
    }

    private void addFirst (Node<T> newNode){
        head.prev = newNode;
        newNode.next = head;
        head = newNode;
    }

    private void addLast (Node<T> newNode){
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    private void removeNode(Node<T> nodeToRemove) {
        if (nodeToRemove == head) {
            head = head.next;
        } else if (nodeToRemove == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
        }
        size--;
    }

}


