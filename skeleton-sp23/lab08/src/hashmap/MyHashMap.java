package hashmap;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private final double loadFactor;
    private int bucketCount;
    private int size;

    /** Constructors */
    public MyHashMap() {
        loadFactor = 0.75;
        bucketCount = 16;
        size = 0;
        buckets = createTable(16);
    }

    public MyHashMap(int initialCapacity) {
        loadFactor = 0.75;
        bucketCount = initialCapacity;
        size = 0;
        buckets = createTable(initialCapacity);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor;
        bucketCount = initialCapacity;
        size = 0;
        buckets = createTable(initialCapacity);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < table.length; i++){
            table[i] = createBucket();
        }
        return table;
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

        int hash = Math.floorMod(key.hashCode(), bucketCount);
        for (Node n : buckets[hash]){
            if (n.key.equals(key)){
                n.value = value;
                return;
            }
        }

        double currLoad = ((double) size + 1) / bucketCount;
        if (currLoad >= loadFactor){
            resize();
            hash = Math.floorMod(key.hashCode(), bucketCount);
        }

        buckets[hash].add(createNode(key, value));
        size += 1;
    }

    private void resize(){
        int newBucketCount = bucketCount * 2;
        Collection<Node>[] newBuckets = createTable(newBucketCount);
        for (Collection<Node> bucket : buckets){
            if (bucket != null){
                for (Node n : bucket){
                    int hash = Math.floorMod(n.key.hashCode(), newBucketCount);
                    newBuckets[hash].add(n);
                }
            }
        }
        this.bucketCount = newBucketCount;
        this.buckets = newBuckets;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int hash = Math.floorMod(key.hashCode(), bucketCount);
        for (Node n : buckets[hash]){
            if (n.key.equals(key)){
                return n.value;
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        int hash = Math.floorMod(key.hashCode(), bucketCount);

        for (Node n : buckets[hash]){
            if (n.key.equals(key)){
                return true;
            }
        }
        return false;
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
        buckets = createTable(16);
        size = 0;
        bucketCount = 16;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 8.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();
        for (Collection<Node> c : buckets){
            for (Node n : c){
                returnSet.add(n.key);
            }
        }
        return returnSet;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        V value = get(key);
        if (containsKey(key)){
            int hash = Math.floorMod(key.hashCode(), bucketCount);
            Collection<Node> bucket = buckets[hash];

            for (Iterator<Node> iterator = bucket.iterator(); iterator.hasNext();){
                Node n = iterator.next();
                if (n.key.equals(key)){
                    iterator.remove();
                    size -= 1;
                }
            }
        }
        return value;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }


    public static void main(String[] args){
        Map61B<String, Integer> b = new MyHashMap<>();
        b.put("hi", 1);;
        for (int i = 0; i < 100; i++) {
            b.put("hi" + i, 1);
        }
    }
}
