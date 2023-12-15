package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);
        // Insert each element to be sorted into a heap (MinFourHeap)
        for (int i = 0; i < array.length; i++){
            heap.add(array[i]);
        }
        // Remove each element from the heap, storing them in order in the original array.
        for (int i = 0; i < array.length; i++){
            array[i] = heap.next();
        }
    }
}
