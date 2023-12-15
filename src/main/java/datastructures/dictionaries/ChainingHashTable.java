package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.NoSuchElementException;

/**
 * - You must implement a generic chaining hashtable. You may not
 *   restrict the size of the input domain (i.e., it must accept
 *   any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given PRIME_SIZES list.
 *   Past this, it should continue to resize using some other mechanism (primes not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 *   dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] arr;
    private int capIndex;

    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        capIndex = 0;
        arr = new Dictionary[PRIME_SIZES[capIndex]];
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null){
            throw new IllegalArgumentException();
        }
        V oldVal = find(key);
        int index = Math.abs(hash(key) % arr.length);
        if (arr[index] == null){
            arr[index] = newChain.get();
        }
        arr[index].insert(key, value);
        if (size / arr.length > 0.75){
            resize();
        }
        if(oldVal == null){
            this.size++;
        }
        return oldVal;
    }

    private void resize(){
        Dictionary<K, V>[] temp;
        if (capIndex < PRIME_SIZES.length) {
            temp = new Dictionary[PRIME_SIZES[capIndex++]];
        } else {
            temp = new Dictionary[arr.length * 2];
        }
        // rehash
        for (Dictionary<K, V> bucket: arr){
            if (bucket != null){
                for (Item<K, V> item: bucket){
                    int index = Math.abs(hash(item.key) % temp.length);
                    if (temp[index] == null){
                        temp[index] = newChain.get();
                    }
                    temp[index].insert(item.key, item.value);
                }
            }
        }
        this.arr = temp;
    }

    @Override
    public V find(K key) {
        if(key == null){
            throw new IllegalArgumentException();
        }
        int index = Math.abs(hash(key) % arr.length);
        V val = null;
        if (arr[index] != null){
            val = arr[index].find(key);
        }
        return val;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new hashIterator();
    }

    private class hashIterator extends SimpleIterator<Item<K, V>>{
        private int currIndex;
        private int bucketNum;
        private Iterator<Item<K,V>>[] iterator;
        public hashIterator(){
            iterator = new Iterator[arr.length];
            currIndex = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != null) {
                    iterator[i] = arr[i].iterator();
                }
            }
        }

        public Item<K, V> next() {
            while (currIndex < arr.length && (iterator[currIndex] == null || !iterator[currIndex].hasNext())) {
                currIndex++;
            }
            bucketNum++;
            return iterator[currIndex].next();
        }

        public boolean hasNext(){
            return (bucketNum < size);
        }

    }

    private int hash(K key){
        return key.hashCode() * 31;
    }
    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
}
