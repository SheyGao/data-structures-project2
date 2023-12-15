package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.misc.DeletelessDictionary;

import java.util.Iterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    private class Node{
        K key;
        V value;
        Node next;

        public Node (K key, V value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node (){
            this.key = null;
            this.value = null;
            this.next = null;
        }
    }

    private Node front;

    public MoveToFrontList(){
        this.front = new Node();
        this.size = 0;
    }
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null){
            throw new IllegalArgumentException();
        }
        V res = null;
        if (front != null && front.key != null && front.key.equals(key)){
            res = front.value;
            front.value = value;
        } else {
            Node prev = front;
            Node curr = front;
            while (curr != null && curr.key!= null && !(curr.key.equals(key))){
                prev = curr;
                curr = curr.next;
            }
            // key found
            if (curr!= null && curr.key != null){
                res = curr.value;
                prev.next = prev.next.next;
                curr.next = front;
                this.front = curr;
                front.value = value;
                // key not found
            } else {
                Node newNode = new Node(key, value, front);
                this.front = newNode;
                size++;
            }
        }
        return res;
        }


    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0){
            return null;
        }
        if (front.key.equals(key)){
            return front.value;
        }else {
            Node prev = front;
            Node curr = front;
            while (curr != null && curr.key != null && !(curr.key.equals(key))){
                prev = curr;
                curr = curr.next;
            }
            // List1: [A] -> [B] -> [C] -> [D]
            // List2: [C] -> [A] -> [B] -> [D]
            if (curr!= null && curr.key != null){
                V res = curr.value;
                prev.next = prev.next.next;
                curr.next = front;
                this.front = curr;
                return res;
            } else {
                return null;
            }
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ListIterator();
    }

    private class ListIterator extends SimpleIterator<Item<K, V>> {
        private Node curr;
        public ListIterator() {
            this.curr = MoveToFrontList.this.front;
        }
        public boolean hasNext() {
            return this.curr != null && this.curr.next != null;
        }
        public Item<K, V> next() {
            Item<K, V> currItem = new Item<>(this.curr.key, this.curr.value);
            this.curr = this.curr.next;
            return currItem;
        }
    }
}
