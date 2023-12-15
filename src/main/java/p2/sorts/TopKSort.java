package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);
        for (int i = 0; i < Math.min(k, array.length); i++){
            heap.add(array[i]);
        }

        for (int i = k; i < array.length; i++){
            E min = heap.peek();
            while (comparator.compare(min, array[i]) < 0){
                heap.next();
                min = array[i];
                heap.add(min);
            }
        }

        //123456
        // heap: 1234
        // min = 1 1 < 5; min = 5; heap: 2345; min=2; 2 < 6 min =6; heap: 3456

        for (int i = 0; i < array.length; i++){
            if (i < k){
                array[i] = heap.next();
            } else {
                array[i] = null;
            }
        }

    }
}
