package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

/**
 *  Implement quicksort. As with the other sorts, your code should be generic.
 *  Your sorting algorithm should meet its expected runtime bound.
 */

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        sorting(array, comparator, 0, array.length - 1);
    }

    private static <E> void sorting(E[] array, Comparator<E> c, int low, int high) {
        if (low < high) {
            int p = partition(array, c, low, high);

            sorting(array, c, low, p - 1);
            sorting(array, c, p + 1, high);

        }
//        int j;
//        for (j = 0; j < array.length; j++) {
//            System.out.println(j + ":" + array[j]);
//        }

    }

    private static <E> int partition(E[] array, Comparator<E> c, int low, int high) {
        int p = low;
        int begin = low + 1;
        int end = high;

        while (begin < end) {
            if (c.compare(array[begin], array[p]) < 0) {
                begin += 1;
            } else {
                E temp = array[begin];
                array[begin] = array[end];
                array[end] = temp;
                end -= 1;
            }
        }

        if (c.compare(array[begin], array[p]) < 0) {
            E temp = array[begin];
            array[begin] = array[p];
            array[p] = temp;
            return begin;
        } else {
            E temp = array[begin - 1];
            array[begin - 1] = array[p];
            array[p] = temp;
            return begin - 1;
        }

    }
}