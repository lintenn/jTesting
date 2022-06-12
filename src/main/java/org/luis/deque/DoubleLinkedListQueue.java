package org.luis.deque;

import java.util.Comparator;
import java.util.LinkedList;

public class DoubleLinkedListQueue<T> implements DoubleEndedQueue<T> {

    private DequeNode<T> left; // or first
    private DequeNode<T> right; // or last
    private int size;

    public DoubleLinkedListQueue() {
        left = null;
        right = null;
        size = 0;
    }

    @Override
    public void append(DequeNode<T> node) {
        if (node == null) {
            throw new RuntimeException("Null element can not be added to Deque.");
        }

        if (right == null) {
            left = right = node;
        } else {
            node.setPrevious(right);
            right.setNext(node);
            right = node;
        }

        size++;
    }

    @Override
    public void appendLeft(DequeNode<T> node) {
        if (node == null) {
            throw new RuntimeException("Null element can not be added to Deque.");
        }

        if (left == null) {
            right = left = node;
        } else {
            node.setNext(left);
            left.setPrevious(node);
            left = node;
        }

        size++;
    }

    @Override
    public void deleteFirst() {
        if (size == 0) {
            throw new RuntimeException("Can not delete on empty Deque.");
        }

        DequeNode<T> temp = left;
        left = left.getNext();

        // If only one element was present
        if (left == null) {
            right = null;
        } else {
            left.setPrevious(null);
        }

        size--;
    }

    @Override
    public void deleteLast() {
        if (size == 0) {
            throw new RuntimeException("Can not delete on empty Deque.");
        }

        DequeNode<T> temp = right;
        right = right.getPrevious();

        // If only one element was present
        if (right == null) {
            left = null;
        } else {
            right.setNext(null);
        }

        size--;
    }

    @Override
    public DequeNode<T> peekFirst() {
        if (size == 0) {
            throw new RuntimeException("Can not peek first on empty Deque.");
        }
        return left;
    }

    @Override
    public DequeNode<T> peekLast() {
        if (size == 0) {
            throw new RuntimeException("Can not peek last on empty Deque.");
        }
        return right;
    }

    @Override
    public int size() {
        return size;
    }

    // Complex operations

    // Returns the node element at given position. Valid positions are between 0 and size - 1.
    @Override
    public DequeNode<T> getAt(int position) {

        if (position < 0 || position >= size) {
            throw new RuntimeException("Invalid position argument: " + position);
        }

        DequeNode<T> node = left;
        for (int i = 1; i <= position; i++) {
            node = node.getNext();
        }

        return node;
    }

    @Override
    public DequeNode<T> find(T item) {
        boolean found = false;
        DequeNode<T> node = left;
        while (node != null && !found) {

            if (node.getItem().equals(item))
                found=true;
            else
                node = node.getNext();
        }

        return node;
    }

    @Override
    public void delete(DequeNode<T> node) {

        if (node.equals(left)) {
            deleteFirst();
        } else if (node.equals(right)) {
            deleteLast();
        } else {
            boolean found=false;
            DequeNode<T> temp = left;
            while (temp != null && !found) {
                if (temp.equals(node)) {
                    temp.getPrevious().setNext(temp.getNext());
                    temp.getNext().setPrevious(temp.getPrevious());
                    found=true;
                }else {
                    temp = temp.getNext();
                }
            }

            if (temp == null) {
                throw new RuntimeException("Can not delete non existing node on list");
            }
            temp = null;
            size--;
        }

    }

    //This method will sort the list in the given order by a comparator.
    @Override
    public void sort(Comparator<T> comparator) {
        DequeNode<T> current = null, index = null;
        T temp;

        if (left != null) {

            //current will point to left
            for (current = left; current.getNext() != null; current = current.getNext()) {

                //index will point to node next to current
                for (index = current.getNext(); index != null; index = index.getNext()) {

                    //If current's item is greater than index's item, swap the item of current and index
                    if (comparator.compare(current.getItem(), index.getItem()) > 0) {
                        temp = current.getItem();
                        current.setItem(index.getItem());
                        index.setItem(temp);
                    }
                }
            }
        }
    }
}
