import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class BSTMapTest {
    @Test
    public void getTester(){
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

        String actual1 = bst.get(5);
        String expected1 = "five";
        assertThat(actual1).isEqualTo(expected1);

        String actual2 = bst.get(14);
        String expected2 = "fourteen";
        assertThat(actual2).isEqualTo(expected2);

        String actual3 = bst.get(6);
        String expected3 = "six";
        assertThat(actual3).isEqualTo(expected3);

        String actual4 = bst.get(1);
        String expected4 = "one";
        assertThat(actual4).isEqualTo(expected4);

        String actual5 = bst.get(69);
        String expected5 = null;
        assertThat(actual5).isEqualTo(expected5);

        String actual6 = bst.get(4);
        String expected6 = "four";
        assertThat(actual6).isEqualTo(expected6);
    }

    @Test
    public void containsTester(){
        BSTMap<Integer, String> bst = new BSTMap<>();
        bst.put(2, "two");
        bst.put(4, "four");
        bst.put(1, "one");
        bst.put(5, "five");
        bst.put(8, "eight");
        bst.put(3, "three");
        bst.put(13, "thirteen");
        bst.put(14, "fourteen");
        bst.put(12, "twelve");
        bst.put(7, "seven");
        bst.put(10, "ten");
        bst.put(11, "eleven");

        boolean actual1 = bst.containsKey(1);
        boolean expected1 = true;
        assertThat(actual1).isEqualTo(expected1);

        boolean actual2 = bst.containsKey(10);
        boolean expected2 = true;
        assertThat(actual2).isEqualTo(expected2);

        boolean actual3 = bst.containsKey(69);
        boolean expected3 = false;
        assertThat(actual3).isEqualTo(expected3);
    }

    @Test
    public void sizeTester(){
        BSTMap<Integer, String> bst = new BSTMap<>();
        bst.put(2, "two");
        bst.put(4, "four");
        bst.put(1, "one");

        int actual1 = bst.size();
        int expected1 = 3;
        assertThat(actual1).isEqualTo(expected1);

        bst.put(5, "five");
        bst.put(8, "eight");
        bst.put(3, "three");
        bst.put(13, "thirteen");
        bst.put(3, "Three");
        bst.put(13, "thirteen");

        int actual2 = bst.size();
        int expected2 = 7;
        assertThat(actual2).isEqualTo(expected2);

        bst.put(14, "fourteen");
        bst.put(12, "twelve");
        bst.put(7, "seven");
        bst.put(10, "ten");
        bst.put(11, "eleven");
        bst.put(12, "telve");
        bst.put(7, "seen");
        bst.put(10, "en");
        bst.put(7, "seen");
        bst.put(10, "en");

        int actual3 = bst.size();
        int expected3 = 12;
        assertThat(actual3).isEqualTo(expected3);

    }

    @Test
    public void clearTester(){
        BSTMap<Integer, String> bst = new BSTMap<>();
        bst.put(2, "two");
        bst.put(4, "four");
        bst.put(1, "one");
        bst.put(5, "five");
        bst.put(8, "eight");
        bst.put(3, "three");
        bst.put(13, "thirteen");
        bst.put(3, "Three");
        bst.put(13, "thirteen");

        int actual1 = bst.size();
        int expected1 = 7;
        assertThat(actual1).isEqualTo(expected1);

        bst.clear();
        int actual2  = bst.size();
        int expected2 = 0;
        assertThat(actual2).isEqualTo(expected2);
    }

    @Test
    public void removeTest(){
        BSTMap<Integer, String> bst = new BSTMap<>();
        bst.put(2, "two");
        bst.put(4, "four");
        bst.put(1, "one");
        bst.put(5, "five");
        bst.put(8, "eight");
        bst.put(3, "three");
        bst.put(13, "thirteen");

        String actual1 = bst.remove(4);
        String expected1 = "four";
        assertThat(actual1).isEqualTo(expected1);

        bst.remove(2);
        bst.remove(3);
        bst.remove(8);

        boolean actual2 = bst.containsKey(1);
        boolean expected2 = true;
        assertThat(actual2).isEqualTo(expected2);

        boolean actual3 = bst.containsKey(13);
        boolean expected3 = true;
        assertThat(actual3).isEqualTo(expected3);

        boolean actual4 = bst.containsKey(5);
        boolean expected4 = true;
        assertThat(actual4).isEqualTo(expected4);

        boolean actual5 = bst.containsKey(8);
        boolean expected5 = false;
        assertThat(actual5).isEqualTo(expected5);

        boolean actual6 = bst.containsKey(2);
        boolean expected6 = false;
        assertThat(actual6).isEqualTo(expected6);

        String actual7 = bst.remove(13);
        String expected7 = "thirteen";
        assertThat(actual7).isEqualTo(expected7);

    }

}
