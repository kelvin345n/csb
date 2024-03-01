package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.List;

public class DummyHistoryTextHandler extends NgordnetQueryHandler {
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        NGramMap ngm = new NGramMap("./data/ngrams/top_14377_words.csv",
                "./data/ngrams/total_counts.csv");

        String response = "";
        for (String word : words){
            response += word + ": " + ngm.weightHistory(word, startYear, endYear).toString() + "\n";
        }

        return response;
    }
}
