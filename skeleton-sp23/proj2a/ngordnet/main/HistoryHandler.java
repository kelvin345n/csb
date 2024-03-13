package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler{
    NGramMap gramMap;
    public HistoryHandler(NGramMap map){
        gramMap = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        for (String word : words) {
            lts.add(gramMap.weightHistory(word, startYear, endYear));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }
}
