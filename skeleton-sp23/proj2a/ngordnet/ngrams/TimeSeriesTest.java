package ngordnet.ngrams;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500


        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }
    @Test
    public void quotientTest(){
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1993, 150.0);
        catPopulation.put(1994, 200.0);

        TimeSeries overallPopulation = new TimeSeries();
        overallPopulation.put(1991, 100.0);
        overallPopulation.put(1992, 1000.0);
        overallPopulation.put(1993, 2000.0);
        overallPopulation.put(1994, 5000.0);

        TimeSeries quotient = catPopulation.dividedBy(overallPopulation);

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1993, 1994));
        assertThat(quotient.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 0.1, 0.075, 0.04));

        for (int i = 0; i < quotient.size(); i += 1) {
            assertThat(quotient.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }
    @Test
    public void quotientTest2(){
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1993, 150.0);
        catPopulation.put(1994, 200.0);

        TimeSeries overallPopulation = new TimeSeries();
        overallPopulation.put(1990, 5000.0);
        overallPopulation.put(1991, 100.0);
        overallPopulation.put(1992, 1000.0);
        overallPopulation.put(1993, 2000.0);
        overallPopulation.put(1994, 5000.0);
        overallPopulation.put(1995, 5000.0);

        TimeSeries quotient = catPopulation.dividedBy(overallPopulation);

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1993, 1994));
        assertThat(quotient.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 0.1, 0.075, 0.04));

        for (int i = 0; i < quotient.size(); i += 1) {
            assertThat(quotient.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }
    @Test
    public void quotientTest3(){
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1993, 150.0);
        catPopulation.put(1994, 200.0);

        TimeSeries overallPopulation = new TimeSeries();
        overallPopulation.put(1990, 5000.0);
        overallPopulation.put(1991, 100.0);
        overallPopulation.put(1992, 1000.0);
        overallPopulation.put(1993, 2000.0);
        // Missing 1994 -> Throw exception
        overallPopulation.put(1995, 5000.0);

        assertThrows(IllegalArgumentException.class, () -> {
            catPopulation.dividedBy(overallPopulation);
        });
    }
} 