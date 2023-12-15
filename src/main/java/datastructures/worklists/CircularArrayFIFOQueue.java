package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private int front;
    private int back;
    private int size;
    private E[] array;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.front = 0;
        this.back = 0;
        this.size = 0;
        this.array = (E[]) new Comparable[capacity];
    }

    @Override
    // add work to the end of circular array
    public void add(E work) {
        if (this.isFull()) {
            throw new IllegalStateException();
        }
        array[back] = work;
        this.back = (back + 1) % array.length;
        this.size++;
    }

    @Override
    // return the first element of the circular array
    public E peek() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        return array[front];
    }

    @Override
    // return the ith element in the circular array
    public E peek(int i) {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return array[(front + i) % array.length];
    }

    @Override
    // return and remove the front element in the circular array
    public E next() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E temp = array[front];
        this.front = (front + 1) % array.length;
        this.size--;
        return temp;
    }

    @Override
    // replace the ith element of the circular array with value
    public void update(int i, E value) {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }
        array[(front + i) % array.length] = value;
    }

    @Override
    // return the number of elements in the circular array
    public int size() {
        return this.size;
    }

    @Override
    // reset the circular array to empty state
    public void clear() {
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int minSize = Math.min(this.size, other.size());
        for (int i = 0; i < minSize; i++) {
            int comparison = this.peek(i).compareTo(other.peek(i));
            if (comparison != 0) {
                return comparison;
            }
        }
        if (this.size < other.size()) {
            return -1;
        } else if (this.size > other.size()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            return this.compareTo(other) == 0;
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int hash = 0;
        for (int i = 0; i < this.size; i++) {
            hash = 31 * hash + array[(front + i) % array.length].hashCode() ;
        }
        return hash;
    }
}

