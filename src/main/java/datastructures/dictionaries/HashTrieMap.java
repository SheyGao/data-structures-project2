package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import cse332.interfaces.misc.Dictionary;
import datastructures.worklists.ArrayStack;

import java.util.HashMap;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            // initialize the pointers field with a new ChainingHashTable instance,
            // and this ChainingHashTable uses the MoveToFrontList as its underlying list implementation
            this.pointers = new ChainingHashTable<A, HashTrieNode>(() -> new MoveToFrontList<>());
            this.value = value;
        }
        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            ArrayStack<Entry<A, HashTrieNode>> entryStack = new ArrayStack<>();
            for(Item<A, HashTrieNode> item : this.pointers) {
                entryStack.add(new AbstractMap.SimpleEntry(item.key, item.value));
            }
            return entryStack.iterator();
        }
    }
        public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode) this.root;
        V val = null;
        for(A element : key){
            if(curr.pointers.find(element) == null){
                curr.pointers.insert(element, new HashTrieNode());
            }
            curr = curr.pointers.find(element);
        }
        val = curr.value;
        curr.value = value;
        if (val == null){
            size++;
        }
        return val;
    }

    @Override
    public V find(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode)this.root;
        for (A element: key){
            curr = curr.pointers.find(element);
            if (curr == null){
                return null;
            }
        }
        return curr.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null){
            throw new IllegalArgumentException();
        }
        HashTrieNode curr = (HashTrieNode)this.root;
        for (A element: key){
            curr = curr.pointers.find(element);
            if(curr == null){
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
//        if (key == null) {
//            throw new IllegalArgumentException();
//        }
//
//        HashTrieNode lastNode = (HashTrieNode)this.root;
//        A lastElement = null;
//        for (A element : key) {
//            lastElement = element;
//        }
//        HashTrieNode curr = (HashTrieNode)this.root;
//
//        for (A element: key) {
//            if (curr == null) {
//                return;
//            }
//            if (curr.value != null || curr.pointers.size() > 1 ) {
//                lastNode = curr;
//                lastElement = element;
//            }
//            curr = curr.pointers.get(element);
//        }
//        if (curr != null && curr.value != null) {
//            if (!curr.pointers.isEmpty()) {
//                curr.value = null;
//            } else if (lastNode != null) {
//                lastNode.pointers.remove(lastElement);
//            }
//            this.size--;
//        }
        throw new UnsupportedOperationException();

    }

    @Override
    public void clear() {
//        this.root = null;
        throw new UnsupportedOperationException();
    }
}

