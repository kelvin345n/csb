package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap gramMap;
    public HistoryTextHandler(NGramMap map) {
        gramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word : words){
            response += word + ": " + gramMap.weightHistory(word, startYear, endYear).toString() + "\n";
        }

        return response;
    }
}
