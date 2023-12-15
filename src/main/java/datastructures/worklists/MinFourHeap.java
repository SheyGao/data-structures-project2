package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private Comparator<E> com;

    public MinFourHeap(Comparator<E> c) {
        this.data = (E[])new Object[10];
        this.size = 0;
        // initializes the com field with the provided comparator (c)
        this.com = c;
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        if(size == data.length){
            E[] newData = (E[]) new Object[data.length*2];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            this.data = newData;
        }
        data[size] = work;
        size++;
        percolateUp();
    }

    public void percolateUp(){
        int i = this.size-1;
        E val = data[i];
        while (i > 0){
            int parent = (i - 1) / 4;
            if (com.compare(data[i], data[parent]) < 0){
                data[i] = data[parent];
                data[parent] = val;
                i = parent;
            }else{
                break;
            }
        }
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        E temp = data[0];
        data[0] = data[size-1];
        size--;
        percolateDown();
        return temp;
    }

    public void percolateDown(){
        int i = 0;
        int leftChild = i*4+1;
        E curr = data[i];
        while (leftChild < size){
            int min = leftChild;
            for (int j = leftChild+1; j < leftChild + 4; j++){
                if (j < size){
                    if (com.compare(data[j], data[min]) < 0)
                        min = j;
                }
            }
            if(com.compare(data[min],curr) < 0){
                data[i] = data[min];
                data[min] = curr;
                i = min;
                leftChild = i*4+1;
            }
            else {
                break;
            }
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.data = (E[])new Comparable[10];
        this.size = 0;
    }
}
