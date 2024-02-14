import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    /** Tests the toList method in ArrayDeque */
    @Test
    public void test_toList(){
        Deque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(13);
        ad.addFirst(12);
        ad.addFirst(11);
        ad.addFirst(10);
        ad.addFirst(9);
        ad.addFirst(8);
        ad.addLast(14);
        List<Integer> actual = ad.toList();
        List<Integer> expected = new ArrayList<>();
        expected.add(8);
        expected.add(9);
        expected.add(10);
        expected.add(11);
        expected.add(12);
        expected.add(13);
        expected.add(14);
        assertThat(actual).isEqualTo(expected);
    }

    /** Tests the isEmpty method in ArrayDeque */
    @Test
    public void test_isEmpty(){
        Deque<String> ad = new ArrayDeque<>();
        boolean actual = ad.isEmpty();
        boolean expected = true;
        assertThat(actual).isEqualTo(expected);

        ad.addLast("Tongue");
        ad.addFirst("Beach");
        boolean actual1 = ad.isEmpty();
        boolean expected1 = false;
        assertThat(actual1).isEqualTo(expected1);
    }

    /** Tests the size method in ArrayDeque */
    @Test
    public void test_size(){
        Deque<String> ad = new ArrayDeque<>();
        int expected = 0;
        int actual = ad.size();
        assertThat(actual).isEqualTo(expected);

        ad.addLast("Tongue");
        int expected1 = 1;
        int actual1 = ad.size();
        assertThat(actual1).isEqualTo(expected1);

        ad.addLast("Tongue");
        ad.addLast("Tongue");
        ad.addLast("Tongue");
        ad.addLast("Tongue");
        ad.addLast("Tongue");
        ad.addLast("Tongue");
        ad.addLast("Tongue");
        ad.addLast("Tongue");
        ad.addLast("Tongue");
        int expected2 = 10;
        int actual2 = ad.size();
        assertThat(actual2).isEqualTo(expected2);
    }

    /** Tests the get method in ArrayDeque */
    @Test
    public void test_get(){
        Deque<String> ad = new ArrayDeque<>();
        String expected = null;
        String actual = ad.get(0);
        assertThat(actual).isEqualTo(expected);

        ad.addFirst("Tongue");
        ad.addFirst("Back");
        ad.addFirst("Brain");
        ad.addFirst("Heart");
        ad.addFirst("Eyes");
        ad.addFirst("Nose");
        ad.addLast("Hair");
        String expected1 = "Hair";
        String actual1 = ad.get(6);
        List<String> actual_list = ad.toList();
        assertThat(actual_list).containsExactly("Nose", "Eyes", "Heart", "Brain",
                                                        "Back", "Tongue", "Hair").inOrder();
        assertThat(actual1).isEqualTo(expected1);

        String expected2 = "Brain";
        String actual2 = ad.get(3);
        assertThat(actual2).isEqualTo(expected2);

        String expected3 = null;
        String actual3 = ad.get(7);
        assertThat(actual3).isEqualTo(expected3);
        actual_list = ad.toList();

        assertThat(actual_list).containsExactly("Nose", "Eyes", "Heart", "Brain",
                                                        "Back", "Tongue", "Hair").inOrder();

        String expected4 = null;
        String actual4 = ad.get(-1);
        assertThat(actual4).isEqualTo(expected4);


    }

    /** Tests the removeFirst method in ArrayDeque */
    @Test
    public void test_removeFirst(){
        Deque<Integer> ad = new ArrayDeque<>();
        Integer actual = ad.removeFirst();
        Integer expected = null;
        assertThat(actual).isEqualTo(expected);

        for (int i = 0; i < 10; i ++){
            ad.addLast(5);
        }

        ad.addFirst(40);
        int actual_size = ad.size();
        int expected_size = 11;
        assertThat(actual_size).isEqualTo(expected_size);

        int actual1 = ad.removeFirst();
        int expected1 = 40;
        assertThat(actual1).isEqualTo(expected1);
        assertThat(ad.removeFirst()).isEqualTo(5);

        actual_size = ad.size();
        expected_size = 9;
        assertThat(actual_size).isEqualTo(expected_size);

        List<Integer> actual_list = ad.toList();
        assertThat(actual_list).containsExactly(5, 5, 5, 5, 5,
                                                        5, 5, 5, 5);

    }

    /** Tests the removeLast method in ArrayDeque */
    @Test
    public void test_removeLast(){
        Deque<Integer> ad = new ArrayDeque<>();
        Integer actual = ad.removeLast();
        Integer expected = null;
        assertThat(actual).isEqualTo(expected);

        for (int i = 0; i < 10; i ++){
            ad.addLast(5);
        }

        ad.addLast(40);
        int actual_size = ad.size();
        int expected_size = 11;
        assertThat(actual_size).isEqualTo(expected_size);

        int actual1 = ad.removeLast();
        int expected1 = 40;
        assertThat(actual1).isEqualTo(expected1);
        assertThat(ad.removeLast()).isEqualTo(5);

        actual_size = ad.size();
        expected_size = 9;
        assertThat(actual_size).isEqualTo(expected_size);

        List<Integer> actual_list = ad.toList();
        assertThat(actual_list).containsExactly(5, 5, 5, 5, 5,
                5, 5, 5, 5);
    }
}
