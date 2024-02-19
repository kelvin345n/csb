import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Collection;
import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {

    public class MaxComparator<T extends Comparable<T>> implements Comparator<T>{
        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p>
         * The implementor must ensure that {@link Integer#signum
         * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * compare(x, y)} must throw an exception if and only if {@code
         * compare(y, x)} throws an exception.)<p>
         * <p>
         * The implementor must also ensure that the relation is transitive:
         * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
         * {@code compare(x, z)>0}.<p>
         * <p>
         * Finally, the implementor must ensure that {@code compare(x,
         * y)==0} implies that {@code signum(compare(x,
         * z))==signum(compare(y, z))} for all {@code z}.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         * @apiNote It is generally the case, but <i>not</i> strictly required that
         * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         */
        @Override
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    }

    public class MinComparator<T extends Comparable<T>> implements Comparator<T>{
        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         * <p>
         * The implementor must ensure that {@link Integer#signum
         * signum}{@code (compare(x, y)) == -signum(compare(y, x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * compare(x, y)} must throw an exception if and only if {@code
         * compare(y, x)} throws an exception.)<p>
         * <p>
         * The implementor must also ensure that the relation is transitive:
         * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
         * {@code compare(x, z)>0}.<p>
         * <p>
         * Finally, the implementor must ensure that {@code compare(x,
         * y)==0} implies that {@code signum(compare(x,
         * z))==signum(compare(y, z))} for all {@code z}.
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         * @apiNote It is generally the case, but <i>not</i> strictly required that
         * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
         * any comparator that violates this condition should clearly indicate
         * this fact.  The recommended language is "Note: this comparator
         * imposes orderings that are inconsistent with equals."
         */
        @Override
        public int compare(T o1, T o2) {
            return -(o1.compareTo(o2));
        }
    }

    @Test
    public void maxTest(){
        MaxComparator<Integer> c = new MaxComparator<>();
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(c);
        mad.addLast(3);
        mad.addLast(4);
        mad.addLast(5);
        mad.addLast(5);
        int actual = mad.max();
        int expected = 5;
        assertThat(actual).isEqualTo(expected);

        MaxComparator<String> maxS = new MaxComparator<>();
        MinComparator<String> minS = new MinComparator<>();
        MaxArrayDeque<String> mad1 = new MaxArrayDeque<>(maxS);
        mad1.addLast("Hi");
        mad1.addLast("Bye");
        mad1.addLast("Birthday");
        mad1.addLast("Zebra");
        String actual2 = mad1.max(minS);
        String expected1 = "Birthday";
        assertThat(actual2).isEqualTo(expected1);
    }

    @Test
    public void minTest() {
        MaxComparator<Integer> maxI = new MaxComparator<>();
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(maxI);
        mad.addLast(3);
        mad.addLast(4);
        mad.addLast(5);
        mad.addLast(5);
        int actual1 = mad.max();
        int expected = 5;
        assertThat(actual1).isEqualTo(expected);

        MinComparator<Integer> minI = new MinComparator<>();
        int actual2 = mad.max(minI);
        int expected1 = 3;
        assertThat(actual2).isEqualTo(expected1);
    }
    // If and only if the Dog class implement Comparables<Dog>
    // Dog[] dogs = new Dog[]{d1, d2, d3};
    // Dog largest = Collection.max(dogs);
}
