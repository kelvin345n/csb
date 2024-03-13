package ngordnet.main;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

public class GraphTest {
    @Test
    public void getNeighborsTest() {
        Graph<Integer> g = new Graph<>();
        // 0 -> 1, 2, 3
        g.addDirectedEdge(0, 1);
        g.addDirectedEdge(0, 2);
        g.addDirectedEdge(0, 3);

        Set<Integer> actual1 = g.getNeighbors(0);
        assertThat(actual1).isEqualTo(Set.of(1, 3, 2));

        assertThat(g.getNeighbors(1)).isEqualTo(Set.of());
        assertThat(g.getNeighbors(2)).isEqualTo(Set.of());
        assertThat(g.getNeighbors(4)).isEqualTo(null);

        g.addDirectedEdge(1, 2);
        g.addDirectedEdge(1, 4);
        assertThat(g.getNeighbors(1)).isEqualTo(Set.of(2, 4));
    }
}
