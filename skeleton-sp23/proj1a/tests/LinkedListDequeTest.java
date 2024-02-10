import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque.
    /** Tests whether toList returns the correct list representation */
    @Test
    public void toListTest(){
        LinkedListDeque<String> lld = new LinkedListDeque<>();
        lld.addLast("Hotdog");
        lld.addLast("Apples");
        lld.addFirst("Peanuts");
        lld.addLast("Churro");
        lld.addFirst("Grapes");

        List<String> actual = lld.toList();

        assertThat(lld.toList()).containsExactly("Grapes", "Peanuts", "Hotdog", "Apples", "Churro").inOrder();
    }

    /** Tests the isEmpty() method in LinkedListDeque */
    @Test
    public void isEmptyTest(){
        /* Test 1 */
        LinkedListDeque<String> lld = new LinkedListDeque<>();
        boolean expected = true;
        boolean actual = lld.isEmpty();
        assertThat(actual).isEqualTo(expected);

        /* Test 2 */
        lld.addLast("Hotdog");
        actual = lld.isEmpty();
        expected = false;
        assertThat(actual).isEqualTo(expected);

        /* Test 3 */
        lld.addLast("Hotdog");
        lld.addFirst("Popcorn");
        actual = lld.isEmpty();
        expected = false;
        assertThat(actual).isEqualTo(expected);
    }

    /** Tests the size() method in LinkedListDeque */
    @Test
    public void sizeTest(){
        /* Test 1 */
        LinkedListDeque<String> lld = new LinkedListDeque<>();
        int expected = 0;
        int actual = lld.size();
        assertThat(actual).isEqualTo(expected);

        /* Test 2 */
        lld.addFirst("Water");
        actual = lld.size();
        expected = 1;
        assertThat(actual).isEqualTo(expected);

        /* Test 3 */
        lld.addLast("Hotdog");
        lld.addFirst("Popcorn");
        lld.addFirst("Pizza");
        lld.addLast("Corndog");
        lld.addLast("Sushi");
        actual = lld.size();
        expected = 6;
        assertThat(actual).isEqualTo(expected);
    }

    /** Tests the removeFirst() method in LinkedListDeque */
    @Test
    public void removeFirstTest(){
        /* Test 1 */
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        Integer actual = lld.removeFirst();
        assertThat(actual).isEqualTo(null);

        /* Test 2 */
        lld.addFirst(9);
        actual = lld.removeFirst();
        Integer expected = 9;
        assertThat(actual).isEqualTo(expected);
        actual = lld.removeFirst();
        assertThat(actual).isEqualTo(null);

        /* Test 3 */
        lld.addLast(8);
        lld.addFirst(7);
        lld.addFirst(6);
        lld.addLast(5);
        lld.addLast(1);
        actual = lld.removeFirst();
        expected = 6;
        assertThat(actual).isEqualTo(expected);

        /* Test 4 */
        assertThat(lld.toList()).containsExactly(7, 8, 5, 1).inOrder();
        actual = lld.removeFirst();
        expected = 7;
        assertThat(actual).isEqualTo(expected);
        assertThat(lld.toList()).containsExactly(8, 5, 1).inOrder();
        int size = lld.size();
        assertThat(size).isEqualTo(3);
    }

    /** Tests the removeLast() method in LinkedListDeque */
    @Test
    public void removeLastTest(){
        /* Test 1 */
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        Integer actual = lld.removeLast();
        Integer actual2 = lld.removeLast();
        assertThat(actual).isEqualTo(null);
        assertThat(actual2).isEqualTo(null);

        /* Test 2 */
        lld.addFirst(9);
        actual = lld.removeLast();
        Integer expected = 9;
        assertThat(actual).isEqualTo(expected);
        actual = lld.removeLast();
        assertThat(actual).isEqualTo(null);

        /* Test 3 */
        lld.addLast(8);
        lld.addFirst(7);
        lld.addFirst(6);
        lld.addLast(5);
        lld.addLast(1);
        actual = lld.removeLast();
        expected = 1;
        assertThat(actual).isEqualTo(expected);

        /* Test 4 */
        assertThat(lld.toList()).containsExactly(6, 7, 8, 5).inOrder();
        actual = lld.removeLast();
        expected = 5;
        assertThat(actual).isEqualTo(expected);
        assertThat(lld.toList()).containsExactly(6, 7, 8).inOrder();
        int size = lld.size();
        assertThat(size).isEqualTo(3);
    }

    /** Tests the get() method in LinkedListDeque */
    @Test
    public void getTest(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertThat(lld.get(0)).isEqualTo(null);
        lld.addFirst(5);
        lld.addLast(10);
        assertThat(lld.get(2)).isEqualTo(null);
        assertThat(lld.get(1)).isEqualTo(10);
        assertThat(lld.get(0)).isEqualTo(5);
        lld.addLast(11);
        lld.addLast(12);
        lld.addLast(14);
        lld.addLast(15);
        assertThat(lld.get(5)).isEqualTo(15);
        lld.addLast(22);
        lld.addLast(21);
        lld.addLast(20);
        assertThat(lld.get(7)).isEqualTo(21);
        assertThat(lld.getRecursive(30)).isEqualTo(null);
    }

    /** Tests the getRecursive() method in LinkedListDeque */
    @Test
    public void getRecursiveTest(){
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        assertThat(lld.getRecursive(0)).isEqualTo(null);
        lld.addFirst(5);
        lld.addLast(10);
        assertThat(lld.getRecursive(2)).isEqualTo(null);
        assertThat(lld.getRecursive(1)).isEqualTo(10);
        assertThat(lld.getRecursive(0)).isEqualTo(5);
        lld.addLast(11);
        lld.addLast(12);
        lld.addLast(14);
        lld.addLast(15);
        assertThat(lld.getRecursive(5)).isEqualTo(15);
        lld.addLast(22);
        lld.addLast(21);
        lld.addLast(20);
        assertThat(lld.getRecursive(7)).isEqualTo(21);
        assertThat(lld.getRecursive(30)).isEqualTo(null);
    }
}
