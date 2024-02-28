import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private int size = 0;
    private Node<K, V> root;
    private class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;
        public Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }
    private Node putHelper(K key, V value, Node n){
        if (n == null){
            size += 1;
            return new Node<>(key, value);
        } else if (n.key.compareTo(key) > 0){
            n.left = putHelper(key, value, n.left);
        } else if (n.key.compareTo(key) < 0){
            n.right = putHelper(key, value, n.right);
        } else{
            n.value = value;
        }
        return n;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }
    public V getHelper(K key, Node n){
        if (n == null){
            return null;
        } else if (n.key.compareTo(key) > 0){
            return (V) getHelper(key, n.left);
        } else if (n.key.compareTo(key) < 0){
            return (V) getHelper(key, n.right);
        } else {
            return (V) n.value;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        if (get(key) == null){
            return false;
        }
        return true;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
       throw new UnsupportedOperationException();
    }


    public static void main(String[] args) {
        BSTMap<Integer, String> bst = new BSTMap<>();
        bst.put(5, "five");
        bst.put(8, "eight");
        bst.put(3, "three");
        bst.put(2, "two");
        bst.put(4, "four");
        bst.put(1, "one");
        bst.put(7, "seven");
        bst.put(10, "ten");
        bst.put(6, "six");
        bst.put(9, "nine");
        bst.put(11, "eleven");
        bst.put(13, "thirteen");
        bst.put(14, "fourteen");
        bst.put(12, "twelve");
        bst.clear();
    }
}
