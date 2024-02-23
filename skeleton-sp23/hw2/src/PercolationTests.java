import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class PercolationTests {
    /** tests the Open() and isOpen() methods */
    @Test
    public void testOpen(){
        Percolation percs = new Percolation(6);
        percs.open(0, 0);
        percs.open(5, 5);
        percs.open(5, 4);
        percs.open(4, 4);
        percs.open(4, 3);
        percs.open(2, 2);
        boolean actual = percs.isOpen(0, 0);
        boolean expected = true;
        assertThat(actual).isEqualTo(expected);

        boolean actual2 = percs.isOpen(5, 5);
        boolean expected2 = true;
        assertThat(actual2).isEqualTo(expected2);

        boolean actual3 = percs.isOpen(4, 5);
        boolean expected3 = false;
        assertThat(actual3).isEqualTo(expected3);

        percs.open(0, 0);
        int openSitesCount_actual = percs.numberOfOpenSites();
        int openSitesCount_expected = 6;
        assertThat(openSitesCount_actual).isEqualTo(openSitesCount_expected);


        boolean connected_actual = percs.wQUset.connected(35, 34);
        boolean connected_expected = true;
        assertThat(connected_actual).isEqualTo(connected_expected);

        boolean connected_actual1 = percs.wQUset.connected(35, 27);
        boolean connected_expected1 = true;
        assertThat(connected_actual1).isEqualTo(connected_expected1);

        boolean connected_actual2 = percs.wQUset.connected(27, 14);
        boolean connected_expected2 = false;
        assertThat(connected_actual2).isEqualTo(connected_expected2);


    }

    @Test
    public void testIsFull(){
        Percolation percs = new Percolation(6);
        percs.open(0, 0);

        boolean actual1 = percs.isFull(0, 0);
        boolean expected1 = true;
        assertThat(actual1).isEqualTo(expected1);

        boolean actual2 = percs.isFull(0, 1);
        boolean expected2 = false;
        assertThat(actual2).isEqualTo(expected2);

        percs.open(1, 0);

        boolean actual3 = percs.isFull(1, 0);
        boolean expected3 = true;
        assertThat(actual3).isEqualTo(expected3);

        percs.open(1, 1);
        percs.open(1, 2);
        percs.open(2, 2);
        boolean actual4 = percs.isFull(2, 2);
        boolean expected4 = true;
        assertThat(actual4).isEqualTo(expected4);
    }

    @Test
    public void testPercolates(){
        Percolation percs = new Percolation(6);
        percs.open(0, 0);
        percs.open(1, 0);
        boolean actual1 = percs.percolates();
        boolean expected1 = false;
        assertThat(actual1).isEqualTo(expected1);

        percs.open(2, 0);
        percs.open(3, 0);
        percs.open(4, 0);
        percs.open(5, 0);
        boolean actual2 = percs.percolates();
        boolean expected2 = true;
        assertThat(actual2).isEqualTo(expected2);

    }
}
