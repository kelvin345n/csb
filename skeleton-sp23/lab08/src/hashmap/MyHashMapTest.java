package hashmap;

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class MyHashMapTest {
    @Test
    public void putAndGetTest(){
        Map61B<Integer, String> map = new MyHashMap<>();
        assertThat(map.size()).isEqualTo(0);

        map.put(1, "one");

        assertThat(map.size()).isEqualTo(1);

        map.put(2, "two");
        map.put(3, "three");
        map.put(10, "ten");

        assertThat(map.size()).isEqualTo(4);

        String actual1 = map.get(1);
        assertThat(actual1).isEqualTo("one");
        String actual2 = map.get(3);
        assertThat(actual2).isEqualTo("three");
        String actual3 = map.get(10);
        assertThat(actual3).isEqualTo("ten");
        String actual4 = map.get(69);
        assertThat(actual4).isEqualTo(null);
        String actual5 = map.get(10);
        assertThat(actual5).isEqualTo("ten");

        map.put(1, "uno");
        assertThat(map.size()).isEqualTo(4);
        String actual6 = map.get(1);
        assertThat(actual6).isEqualTo("uno");
    }

    @Test
    public void keySetTest(){
        Map61B<Integer, String> map = new MyHashMap<>();

        Set<Integer> actual3 = map.keySet();
        assertThat(actual3).isEqualTo(Set.of());

        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(10, "ten");
        map.put(1, "uno");
        map.put(2, "dos");

        Set<Integer> actual1 = map.keySet();
        assertThat(actual1).isEqualTo(Set.of(10, 2, 3, 1));

        map.put(4, "three");
        map.put(9, "ten");

        Set<Integer> actual2 = map.keySet();
        assertThat(actual2).isEqualTo(Set.of(10, 2, 3, 1, 4, 9));
    }

    @Test
    public void removeTest(){
        Map61B<Integer, String> map = new MyHashMap<>();

        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(10, "ten");

        assertThat(map.remove(1)).isEqualTo("one");
        assertThat(map.size()).isEqualTo(3);
        assertThat(map.keySet()).isEqualTo(Set.of(2, 3, 10));

        assertThat(map.get(1)).isEqualTo(null);

        assertThat(map.remove(1)).isEqualTo(null);
        assertThat(map.size()).isEqualTo(3);
        assertThat(map.keySet()).isEqualTo(Set.of(2, 3, 10));
    }


    @Test
    public void resizeTest(){
        Map61B<String, Integer> map = new MyHashMap<>(4, 1);

        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);

        assertThat(map.containsKey("four")).isEqualTo(true);
        assertThat(map.get("one")).isEqualTo(1);
    }

    @Test
    public void testSize() {
        sanitySizeTest(new MyHashMap<>());
    }
    public static void sanitySizeTest(MyHashMap<String, Integer> b) {
        b.put("hi", 1);
        assertThat(b.size()).isEqualTo(1);
        for (int i = 0; i < 100; i++) {
            b.put("hi" + i, 1);
        }
        assertThat(b.size()).isEqualTo(101);
    }

}
