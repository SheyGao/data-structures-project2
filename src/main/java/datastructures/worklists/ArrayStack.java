package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] array;
    private int top;

    public ArrayStack() {
        this.array = (E[])new Object[10];
        this.top = -1;
    }

    @Override
    // Adds work to the top of array stack
    public void add(E work) {
        if (this.top + 1 == array.length){
            E[]newArray = (E[])new Object[array.length*2];
            for (int i = 0; i < this.array.length; i++){
                newArray[i] = array[i];
            }
            this.array = newArray;
        }
        top++;
        array[top] = work;
    }

    @Override
    // Returns the top element of the array stack
    public E peek() {
        if(!this.hasWork()){
            throw new NoSuchElementException();
        }
        return array[top];
    }

    @Override
    // Returns and removes the top element of the array stack
    public E next() {
        if(!this.hasWork()){
            throw new NoSuchElementException();
        }
        E temp = array[top];
        this.top--;
        return temp;
    }

    @Override
    // return the number of elements of the array stack
    public int size() {
        return this.top + 1;
    }

    @Override
    // reset the array stack to empty state
    public void clear() {
        this.top = -1;
    }
}

