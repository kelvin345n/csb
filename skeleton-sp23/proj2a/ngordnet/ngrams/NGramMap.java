package ngordnet.ngrams;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;

    // TODO: Add any necessary static/instance variables.

    private final String wordsFilename;
    private final String countsFilename;

    // Records a HashMap of the word that corresponds to its TimeSeries
    Map<String, TimeSeries> wordsMap;

    // Records the total amount of words in that year. <year, frequency of word>
    TimeSeries totalFrequency;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordsFilename = wordsFilename;
        this.countsFilename = countsFilename;

        wordsMap = new HashMap<>();
        totalFrequency = new TimeSeries();
        readCountFile();
        readWordsFile();
    }

    /** Reads in the COUNTSFILENAME and populates the totalFrequency TimeSeries */
    private void readCountFile(){
        In in = new In(countsFilename);

        while(in.hasNextLine()){
            String line = in.readLine();
            String[] parts = line.split(",");

            int year = Integer.parseInt(parts[0]);
            double frequency = Double.parseDouble(parts[1]);

            totalFrequency.put(year, frequency);
        }
    }
    /** Reads in WORDSFILENAME and populates the wordsMap HashMap */
    private void readWordsFile() {
        In in = new In(wordsFilename);

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split("\\s+");

            String word = parts[0];
            int year = Integer.parseInt(parts[1]);
            double frequency = Double.parseDouble(parts[2]);

            // If word is not in WORDSMAP then create a time series for it and put it into WORDSMAP
            if (!wordsMap.containsKey(word)) {
                wordsMap.put(word, new TimeSeries());
            }
            // For the specific WORD this is its corresponding TimeSeries in the wordsMap
            TimeSeries wordTimeSeries = wordsMap.get(word);
            wordTimeSeries.put(year, frequency);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries wordSeries = wordsMap.get(word);
        if (wordSeries == null){
            return null;
        }
        return new TimeSeries(wordSeries, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries wordSeries = wordsMap.get(word);
        if (wordSeries == null){
            return null;
        }
        return new TimeSeries(wordSeries, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalFrequency, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordSeries = wordsMap.get(word);
        if (wordSeries == null){
            return new TimeSeries();
        }
        TimeSeries tempSeries = new TimeSeries(wordSeries, startYear, endYear);
        return tempSeries.dividedBy(totalFrequency);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordSeries = wordsMap.get(word);
        if (wordSeries == null){
            return new TimeSeries();
        }
        return wordSeries.dividedBy(totalFrequency);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries returnSeries = new TimeSeries();
        for (String word : words){
            TimeSeries wordSeries = wordsMap.get(word);
            if (wordSeries == null){
                continue;
            }
            returnSeries = returnSeries.plus(weightHistory(word));
        }
        return new TimeSeries(returnSeries, startYear, endYear);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries returnSeries = new TimeSeries();
        for (String word : words){
            TimeSeries wordSeries = wordsMap.get(word);
            if (wordSeries == null){
                continue;
            }
            returnSeries = returnSeries.plus(weightHistory(word));
        }
        return new TimeSeries(returnSeries, MIN_YEAR, MAX_YEAR);
    }
}
