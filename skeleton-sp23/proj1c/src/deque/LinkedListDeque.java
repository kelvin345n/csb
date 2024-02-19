package deque;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T> {
    private Node<T> sentinel;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(T i, Node<T> n, Node<T> p){
            item = i;
            next = n;
            prev = p;
        }
    }
    /** Constructor for LinkedListDeque class */
    public LinkedListDeque() {
        sentinel = new Node<T>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     * @param x item to add
     */
    public void addFirst(T x) {
        size += 1;
        sentinel.next = new Node<T>(x, sentinel.next, sentinel);
        sentinel.next.next.prev = sentinel.next;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     * @param x item to add
     */
    public void addLast(T x) {
        size += 1;
        sentinel.prev = new Node<T>(x, sentinel, sentinel.prev);
        sentinel.prev.prev.next = sentinel.prev;
    }

    /**
     * Returns a List copy of the deque. Does not alter tne deque.
     * @return a new list copy of the deque.
     */
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node<T> p = sentinel.next;
        while (p != sentinel){
            returnList.add(p.item);
            p = p.next;
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    public boolean isEmpty() {
        if (sentinel.next == sentinel){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     * @return the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     * @return removed element, otherwise {@code null}.
     */
    public T removeFirst() {
        if (isEmpty()){
            return null;
        } else {
            T removedItem = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return removedItem;
        }
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     * @return removed element, otherwise {@code null}.
     */
    public T removeLast() {
        if (isEmpty()){
            return null;
        } else {
            T removedItem = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return removedItem;
        }
    }

    /**
     * The Deque abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively.Does
     * not alter the deque.
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */
    public T get(int index) {
        Node<T> p = sentinel.next;
        while (index > 0 ) {
            if (p == sentinel){
                return null;
            }
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively.
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */
    public T getRecursive(int index) {
        T item = getRecursiveHelper(index, sentinel.next);
        return item;
    }

    public T getRecursiveHelper(int index, Node<T> n){
        if (n == sentinel) {
            return null;
        }
        if (index <= 0){
            return n.item;
        } else {
            return getRecursiveHelper(index-1, n.next);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>();
    }

    public class LinkedListIterator<T> implements Iterator<T> {
        private Node<T> pointer;
        private int sizeLeft;

        public LinkedListIterator() {
            pointer = (Node<T>) sentinel;
            sizeLeft = 0;
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
           if (sizeLeft >= size){
               return false;
           } else {
               return true;
           }
        }
        /**
         * Returns the next element in the iteration.
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (hasNext()){
                pointer = pointer.next;
                sizeLeft += 1;
                return pointer.item;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){return true;}
        if (obj instanceof LinkedListDeque lld){
            if (this.size() != lld.size()) {return false;}
            return equalHelper(lld);
        }
        return false;
    }
    private boolean equalHelper(LinkedListDeque lld){
        if (this.isEmpty()) {return true;} // means both are empty because both have same size
        Node<T> p1 = sentinel.next;
        Node<T> p2 = lld.sentinel.next;
        while (p1 != sentinel){
            if (p1.item != p2.item){
                return false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        return true;
    }

    @Override
    public String toString(){
        return toList().toString();
    }

    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(0);
        lld.addLast(4);
        lld.addLast(6);
        lld.addFirst(6);
        Integer r = lld.removeLast();

        System.out.println(lld);
    }
}
