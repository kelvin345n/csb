import edu.princeton.cs.algs4.BST;

import java.util.*;

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
        Set<K> returnSet = new LinkedHashSet<>();
        keySetHelper(root, returnSet);
        return returnSet;
    }
    private void keySetHelper(Node n, Set set){
        if (n == null){
            return;
        }
        keySetHelper(n.left, set);
        set.add(n.key);
        keySetHelper(n.right, set);
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
        Node removal = findNode(key, root);
        if (removal == null){
            return null;
        }
        V originalValue = (V) removal.value;
        int numChildren = numberOfChildren(removal);

        if (removal == root){
            if (numChildren == 0){
                root = null;
            } else if (numChildren == 1){
                if (removal.left == null ) {
                    root = removal.right;
                } else {
                    root = removal.left;
                }
            } else { // two children means Hibbards Deletion
                hibbardDeletion(removal);
            }
            return originalValue;
        }

        // If the removal node is not the root and exists, then
        // it must have a parent that is not null.
        Node parent = findParent(removal, root);

        // Determines if the child is on the left or the right of the parent.
        if (removal.key.compareTo(parent.key) < 0){
            // This means the child is on the left
            if (numChildren == 0){
                parent.left = null;
            } else if (numChildren == 1){
                if (removal.left == null ) {
                    parent.left = removal.right;
                } else {
                    parent.left = removal.left;
                }
            } else { // two children means Hibbards Deletion
                hibbardDeletion(removal);
            }
        } else {
            // This means the child is on the right
            if (numChildren == 0){
                parent.right = null;
            } else if (numChildren == 1){
                if (removal.left == null ) {
                    parent.right = removal.right;
                } else {
                    parent.right = removal.left;
                }
            } else { // two children means Hibbards Deletion
                hibbardDeletion(removal);
            }
        }
        return originalValue;
    }

    /** Finds the node that should replace the removable node.
     * The logic for the deletion is in the remove() method */
    private void hibbardDeletion(Node removal){
        Node successor =  hibbardDeletionHelper(removal.right);
        remove((K) successor.key);
        removal.key = successor.key;
        removal.value = successor.value;
    }
    private Node hibbardDeletionHelper(Node n){
        if (n.left == null){
            return n;
        }
        return hibbardDeletionHelper(n.left);
    }

    /** Finds and returns a node when given a key and the root node
     * Null if none found.
     * */
    private Node findNode(K key, Node n){
        if (n == null){
            return null;
        } else if (n.key.compareTo(key) > 0){
            return findNode(key, n.left);
        } else if (n.key.compareTo(key) < 0){
            return findNode(key, n.right);
        } else {
            return n;
        }
    }

    /** When given a node, such as removal. This method finds the "child"
     * node's "parent" and returns it. */
    private Node findParent(Node child, Node parent){
        if (child == root) return null;
        if (parent.left == child || parent.right == child){
            return parent;
        } else if (parent.key.compareTo(child.key) > 0){
            return findParent(child, parent.left);
        } else {
            return findParent(child, parent.right);
        }
    }

    /** Calculates the number of children a node has */
    private int numberOfChildren(Node n){
        int count = 0;
        if (n.left != null){
            count++;
        }
        if (n.right != null){
            count++;
        }
        return count;
    }

    
    /**
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
       return keySet().iterator();
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
        for (int i: bst){
            System.out.print(bst.get(i) + " ");
        }
    }
}
