import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    /** Tests whether the ArrayDeque can be iterated over */
    @Test
    public void iteratorTest(){
        /* Test 1: checks if correct sum is printed using a foreach loop */
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(6);
        ad.addLast(7);
        ad.addLast(8);
        ad.addLast(9);
        ad.addFirst(5);
        int expectedSum = 50;
        int actualSum = 0;
        for (int i : ad) {
            actualSum += i;
        }
        assertThat(actualSum).isEqualTo(expectedSum);

        /* Test 2: Tests if iteration works by using JUnit */
        Deque<String> ad2 = new ArrayDeque<>();
        ad2.addLast("front"); // after this call we expect: ["front"]
        ad2.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad2.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad2).containsExactly("front", "middle", "back");
    }

    /** Tests whether the isEqual method works */
    @Test
    public void isEqualTest(){
        /* Test 1 */
        Deque<String> ad1 = new ArrayDeque<>();
        Deque<String> ad2 = new ArrayDeque<>();

        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");

        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        assertThat(ad1).isEqualTo(ad2);

        /* Test 2 */
        ArrayDeque<Boolean> ad3 = new ArrayDeque<>();
        ArrayDeque<Boolean> ad4 = new ArrayDeque<>();

        ad3.addLast(true);
        ad3.addLast(false);
        ad3.addLast(false);

        ad4.addLast(true);
        ad4.addLast(false);
        ad4.addLast(true);


        assertThat(ad3.equals(ad4)).isEqualTo(false);

        /* Test 3 */
        Deque<String> ad6 = new ArrayDeque<>();
        Deque<String> ad5 = new ArrayDeque<>();

        assertThat(ad5).isEqualTo(ad6);
    }

    /** Tests whether the toString method works */
    @Test
    public void toStringTest(){
        Deque<String> ad1 = new ArrayDeque<>();

        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");

        String expected = "[front, middle, back]";
        String actual = ad1.toString();

        assertThat(actual).isEqualTo(expected);

    }
}

