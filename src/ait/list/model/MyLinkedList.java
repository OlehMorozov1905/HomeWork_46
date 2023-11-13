package ait.list.model;

import ait.list.interfaces.IList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class MyLinkedList<E> implements IList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;

    @Override
//    O(1)
    public int size() {
        return size;
    }

    @Override
//    O(1)
    public boolean add(E element) {
        Node<E> newNode = new Node<>(last, element, null);
        if (last != null) {
            last.next = newNode;
        } else {
            first = newNode;
        }
        last = newNode;
        size++;
        return true;
    }

    @Override
//    O(n)
    public void clear() {
        // TODO
        Node<E> victim = first;
        while (victim != null) {
            Node<E> next = victim.next;
            victim.data = null;
            victim.prev = null;
            victim.next = null;
            victim = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
//    O(n)
    public boolean add(int index, E element) {
        if(index == size){
            return add(element);
        }
        // TODO
        Node<E> cur = getNodeByIndex(index);
        Node<E> prev = cur.prev;
        Node<E> newNode = new Node<>(prev, element, cur);

        if (prev != null) {
            prev.next = newNode;
        } else {
            first = newNode;
        }

        cur.prev = newNode;
        size++;
        return true;
    }

    @Override
//    O(n)
    public E get(int index) {
        Node<E> node = getNodeByIndex(index);
        return node.data;
    }

    private Node<E> getNodeByIndex(int index) {
        checkIndex(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
    }

    @Override
//    O(n)
    public int indexOf(Object o) {
        int index = 0;
        if (o != null) {
            for (Node<E> node = first; node != null; node = node.next, index++) {
                if (o.equals(node.data)) {
                    return index;
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next, index++) {
                if (o == node.data) {
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
//    O(n)
    public int lastIndexOf(Object o) {
        // TODO
        int index = size - 1;
        if (o != null) {
            for (Node<E> node = last; node != null; node = node.prev, index--) {
                if (o.equals(node.data)) {
                    return index;
                }
            }
        } else {
            for (Node<E> node = last; node != null; node = node.prev, index--) {
                if (o == node.data) {
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
//    O(n)
    public E remove(int index) {
        Node<E> node = getNodeByIndex(index);
        return unLink(node);
    }
    private E unLink(Node<E> node) {
        E victim = node.data;
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if (prev != null) {
            prev.next = next;
            node.prev = null;
        } else {
            first = next;
        }
        if (next != null) {
            next.prev = prev;
            node.next = null;
        } else {
            last = prev;
        }
        node.data = null;
        size--;
        return victim;
    }

    @Override
//    O(n)
    public E set(int index, E element) {
        Node<E> node = getNodeByIndex(index);
        E victim = node.data;
        node.data = element;
        return victim;
    }

    @Override
//    O(1)
    public Iterator<E> iterator() {
        // TODO
        return new Iterator<E>() {
            private Node<E> cur = first;
            @Override
            public boolean hasNext() {
                return cur != null;
            }
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = cur.data;
                cur = cur.next;
                return data;
            }
        };
    }

    private static class Node<E> {
        E data;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}

