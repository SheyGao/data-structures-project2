package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    // inner node class
    private static class Node<E>{
        E work;
        Node<E> next;

        public Node(E work){
            this.work = work;
            this.next = null;
        }
    }
    private Node<E> front;
    private Node<E> back;
    private int size;

    public ListFIFOQueue() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    // Adds work to the end of queue
    public void add(E work) {
        Node<E> newNode = new Node<E>(work);
        if(this.hasWork()){
            this.back.next = newNode;
            this.back = this.back.next;
        }else{
            this.front = newNode;
            this.back = newNode;
        }
        this.size++;
    }

    @Override
    // Returns the first work of the queue
    public E peek() {
        if(!this.hasWork()){
            throw new NoSuchElementException();
        }
        return this.front.work;
    }

    @Override
    // Removes and returns the next work to work on; must remove from the "beginning"
    public E next() {
        // no node in the worklist
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        E temp = this.front.work;
        // one node in the worklist
        if (this.front == this.back) {
            this.back = null;
        }
        this.front = this.front.next;
        this.size--;
        return temp;
    }


    @Override
    // return the number of element in the queue
    public int size() {
        return this.size;
    }

    @Override
    // reset the queue to empty state
    public void clear() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }
}

