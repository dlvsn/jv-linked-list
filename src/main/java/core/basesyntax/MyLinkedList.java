package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> element = new Node<>(null, value, null);
        if (head == null) {
            head = tail = element;
        } else {
            tail.next = element;
            element.prev = tail;
            tail = element;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAddition(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = (index < size / 2) ? getNodeFromHead(index) : getNodeFromTail(index);
        Node<T> newElement = new Node<>(current.prev, value, current);
        if (current.prev != null) {
            current.prev.next = newElement;
        } else {
            head = newElement;
        }
        current.prev = newElement;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = (index < size / 2) ? getNodeFromHead(index) : getNodeFromTail(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = (index < size / 2) ? getNodeFromHead(index) : getNodeFromTail(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = (index < index / 2) ? getNodeFromHead(index) : getNodeFromTail(index);
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedElement = head;
        for (int i = 0; i < size; i++) {
            if (removedElement.value == object
                    || object != null
                    && object.equals(removedElement.value)) {
                remove(i);
                return true;
            }
            removedElement = removedElement.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " is out of bounds. "
                    + size);
        }
    }

    private void checkIndexForAddition(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index: "
                    + index
                    + " is out of bounds. "
                    + size);
        }
    }

    private static class Node<E> {
        private E value;
        private Node<E> prev;
        private Node<E> next;

        public Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
