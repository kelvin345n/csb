package ngordnet.main;

import ngordnet.ngrams.NGramMap;
import org.junit.jupiter.api.Test;
import java.util.*;
import static com.google.common.truth.Truth.assertThat;

public class WordNetTest {
    @Test
    public void getSynonymsTest() {
        WordNet wn = new WordNet("./data/wordnet/synsets11.txt",
                "./data/wordnet/hyponyms11.txt");

        List<String> actual1 = wn.getSynonyms("action");
        Collections.sort(actual1);
        List<String> expected1 = new ArrayList<>();
        expected1.add("action");
        assertThat(actual1).isEqualTo(expected1);

        List<String> actual2 = wn.getSynonyms("jump");
        Collections.sort(actual2);
        List<String> expected2 = new ArrayList<>();
        expected2.add("jump");
        expected2.add("leap");
        expected2.add("parachuting");
        assertThat(actual2).isEqualTo(expected2);

        List<String> actual3 = wn.getSynonyms("leap");
        Collections.sort(actual3);
        List<String> expected3 = new ArrayList<>();
        expected3.add("jump");
        expected3.add("leap");
        assertThat(actual3).isEqualTo(expected3);

        List<String> actual4 = wn.getSynonyms("demotion");
        Collections.sort(actual4);
        List<String> expected4 = new ArrayList<>();
        expected4.add("demotion");
        assertThat(actual4).isEqualTo(expected4);
    }
    @Test
    public void getHyponymsTest() {
        WordNet wn = new WordNet("./data/wordnet/synsets11.txt",
                "./data/wordnet/hyponyms11.txt");

        List<String> actual1 = wn.getHyponyms("action");
        Collections.sort(actual1);
        List<String> expected1 = new ArrayList<>();
        expected1.add("change");
        expected1.add("demotion");
        assertThat(actual1).isEqualTo(expected1);

        List<String> actual2 = wn.getHyponyms("change");
        Collections.sort(actual2);
        List<String> expected2 = new ArrayList<>();
        expected2.add("demotion");
        assertThat(actual2).isEqualTo(expected2);

        List<String> actual3 = wn.getHyponyms("demotion");
        Collections.sort(actual3);
        List<String> expected3 = new ArrayList<>();
        assertThat(actual3).isEqualTo(expected3);

        List<String> actual4 = wn.getHyponyms("increase");
        Collections.sort(actual4);
        List<String> expected4 = new ArrayList<>();
        expected4.add("augmentation");
        expected4.add("jump");
        expected4.add("leap");
        assertThat(actual4).isEqualTo(expected4);


        WordNet wn2 = new WordNet("./data/wordnet/synsets16.txt",
                "./data/wordnet/hyponyms16.txt");

        List<String> actual5 = wn2.getHyponyms("change");
        Collections.sort(actual5);
        List<String> expected5 = new ArrayList<>();
        expected5.add("demotion");
        expected5.add("increase");
        expected5.add("jump");
        expected5.add("leap");
        expected5.add("saltation");
        expected5.add("transition");
        expected5.add("variation");
        assertThat(actual5).isEqualTo(expected5);

        List<String> actual6 = wn2.getHyponyms("alteration");
        Collections.sort(actual6);
        List<String> expected6 = new ArrayList<>();
        expected6.add("conversion");
        expected6.add("increase");
        expected6.add("jump");
        expected6.add("leap");
        expected6.add("mutation");
        expected6.add("saltation");
        expected6.add("transition");
        assertThat(actual6).isEqualTo(expected6);

        List<String> actual7 = wn2.getHyponyms("a");
        Collections.sort(actual7);
        List<String> expected7 = new ArrayList<>();
        assertThat(actual7).isEqualTo(expected7);
    }
    @Test
    public void getEverythingTest(){
        WordNet wn2 = new WordNet("./data/wordnet/synsets16.txt",
                "./data/wordnet/hyponyms16.txt");

        List<String> actual3 = wn2.getEverything("transition");
        List<String> expected3 = new ArrayList<>();
        expected3.add("flashback");
        expected3.add("jump");
        expected3.add("leap");
        expected3.add("saltation");
        expected3.add("transition");
        assertThat(actual3).isEqualTo(expected3);

        List<String> actual4 = wn2.getEverything("flashback");
        List<String> expected4 = new ArrayList<>();
        expected4.add("flashback");
        assertThat(actual4).isEqualTo(expected4);

        List<String> actual5 = wn2.getEverything("change");
        List<String> expected5 = new ArrayList<>();
        expected5.add("alteration");
        expected5.add("change");
        expected5.add("demotion");
        expected5.add("increase");
        expected5.add("jump");
        expected5.add("leap");
        expected5.add("modification");
        expected5.add("saltation");
        expected5.add("transition");
        expected5.add("variation");
        assertThat(actual5).isEqualTo(expected5);

        List<String> actual6 = wn2.getEverything("alteration");
        List<String> expected6 = new ArrayList<>();
        expected6.add("adjustment");
        expected6.add("alteration");
        expected6.add("change");
        expected6.add("conversion");
        expected6.add("increase");
        expected6.add("jump");
        expected6.add("leap");
        expected6.add("modification");
        expected6.add("mutation");
        expected6.add("saltation");
        expected6.add("transition");
        assertThat(actual6).isEqualTo(expected6);

        List<String> actual7 = wn2.getEverything("a");
        List<String> expected7 = new ArrayList<>();
        assertThat(actual7).isEqualTo(expected7);
    }
}
