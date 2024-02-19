import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;


public class LinkedListDequeTest {
    /** Tests whether the LinkedListDeque can be iterated over */
    @Test
    public void iteratorTest(){
        /* Test 1: Iterates over list and finds sum */
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        lld.addLast(4);
        lld.addLast(5);
        lld.addLast(6);
        lld.addLast(7);
        lld.addLast(8);
        lld.addLast(9);
        lld.addFirst(5);
        int expectedSum = 50;
        int actualSum = 0;
        for (int i : lld) {
            actualSum += i;
        }
        assertThat(actualSum).isEqualTo(expectedSum);

        /* Test 2: String iteration using JUnit*/
        Deque<String> lld1 = new LinkedListDeque<>();
        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    /** Tests whether the isEqual method works */
    @Test
    public void isEqualTest(){
        /* Test 1 */
        Deque<String> lld1 = new LinkedListDeque<>();
        Deque<String> lld2 = new LinkedListDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);

        /* Test 2 */
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<>();
        LinkedListDeque<Boolean> lld4 = new LinkedListDeque<>();

        lld3.addLast(true);
        lld3.addLast(false);
        lld3.addLast(true);

        lld4.addLast(true);
        lld4.addLast(false);
        lld4.addLast(true);

        Boolean actual1 = lld3 == lld4;
        assertThat(actual1).isEqualTo(true);

        /* Test 3 */
        LinkedListDeque<Boolean> lld5 = new LinkedListDeque<>();
        LinkedListDeque<Boolean> lld6 = new LinkedListDeque<>();
        assertThat(lld5).isEqualTo(lld6);
    }

    /** Tests whether the toString method works */
    @Test
    public void toStringTest(){
        Deque<String> lld1 = new LinkedListDeque<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        String expected = "[front, middle, back]";
        String actual = lld1.toString();

        assertThat(actual).isEqualTo(expected);
    }

}
