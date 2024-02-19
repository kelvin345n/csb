package deque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Deque<T> {

    private T[] arraydeque;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Creates an Array of size 10 however no
     * elements instantiated inside arraydeque */
    public ArrayDeque(){
        size = 0;
        arraydeque = (T[])new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        resize_up_if_needed();
        size += 1;
        arraydeque[nextFirst] = x;
        nextFirst = (nextFirst - 1 + arraydeque.length) % arraydeque.length;
    }
    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        resize_up_if_needed();
        size += 1;
        arraydeque[nextLast] = x;
        nextLast = (nextLast + 1) % arraydeque.length;
    }

    private void resize_up_if_needed(){
        /* Makes arraydeque two times longer if the original array is full */
        if (size >= arraydeque.length){
            T[] arraydeque_cpy = (T[])new Object[arraydeque.length*2];
            int first = (nextFirst + 1) % arraydeque.length;
            for (int i = 0; i < arraydeque.length; i++){
                arraydeque_cpy[i] = arraydeque[(first) % arraydeque.length];
                first = (first + 1) % arraydeque.length;
            }
            nextLast = arraydeque.length;
            nextFirst = arraydeque_cpy.length - 1;
            arraydeque = arraydeque_cpy;
        }
    }

    /**
     * Returns a List copy of the deque. Does not alter tne deque.
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int first = (nextFirst + 1) % arraydeque.length;
        for (int i = 0; i < size; i++){
            returnList.add(arraydeque[(first) % arraydeque.length]);
            first = (first + 1) % arraydeque.length;
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        if (size == 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()){
            return null;
        }
        size -= 1;
        T temp = arraydeque[(nextFirst + 1) % arraydeque.length];
        arraydeque[(nextFirst + 1) % arraydeque.length] = null;
        nextFirst = (nextFirst + 1) % arraydeque.length;
        resize_down_if_needed();
        return temp;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (isEmpty()){
            return null;
        }
        size -= 1;
        T temp = arraydeque[(nextLast - 1) % arraydeque.length];
        arraydeque[(nextLast - 1) % arraydeque.length] = null;
        nextLast = (nextLast - 1) % arraydeque.length;
        resize_down_if_needed();
        return temp;
    }

    /** Only called in removeLast and removeFirst. Resizes if ratio
     * is < 0.25 and array length greater than 9  */
    private void resize_down_if_needed(){
        /* If the ratio of items in arraydeque is less than or equal to
         * 0.25, then we create a new arraydeque with half the original size
         * unless the arraydeque has a length of 8 (Starting Length)*/
        if (arraydeque.length <= 8){
            return;
        } else {
            double ratio = (double)size / arraydeque.length;
            if (ratio <= 0.25){
                T[] arraydeque_cpy = (T[])new Object[arraydeque.length/2];
                int first = (nextFirst + 1) % arraydeque.length;
                for (int i = 0; i < size; i++){
                    arraydeque_cpy[i] = arraydeque[(first) % arraydeque.length];
                    first = (first + 1) % arraydeque.length;
                }
                nextLast = size;
                nextFirst = arraydeque_cpy.length - 1;
                arraydeque = arraydeque_cpy;
            }
        }
    }

    /**
     * The Deque abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element. Does
     * not alter the deque.
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        } else {
            int item = (nextFirst + 1 + index) % arraydeque.length;
            return arraydeque[item];
        }
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively.
     *
     * @param index index to get, assumes valid index
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator<>();
    }

    public class ArrayDequeIterator<T> implements Iterator<T> {
        private int index;
        private int sizeLeft;

        public ArrayDequeIterator() {
            index = -1;
            sizeLeft = 0;
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if (sizeLeft == size){
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
                index += 1;
                sizeLeft += 1;
                return (T) get(index);
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {return true;}
        if (obj instanceof ArrayDeque ad) {
            if (this.size() != ad.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i++){
                if (this.get(i) != ad.get(i)){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return toList().toString();
    }

    public static void main(String[] args){
        Deque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(13);
        ad.addFirst(12);
        ad.addFirst(11);
        ad.addFirst(10);
        ad.addFirst(9);
        ad.addFirst(8);
        ad.addLast(14);
        ad.addLast(15);
        ad.addLast(16);

        System.out.println(ad);

    }
}