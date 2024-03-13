package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class HyponymHandler extends NgordnetQueryHandler {
    private WordNet wNet;
    private NGramMap ngm;

    /** This is used, so we can place words into a maxHeap with their
     * respective counts. And we can poll from the maxHeap using a
     * comparator of the maxHeapNode class */
    private class maxHeapNode {
        private String word;
        private double count;

        public maxHeapNode(String word, double count){
            this.word = word;
            this.count = count;
        }
        public String getWord(){
            return word;
        }
        public double getCount(){
            return count;
        }
    }
    public HyponymHandler(WordNet net, NGramMap ngm){
        wNet = net;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        Comparator<maxHeapNode> comparator = Comparator.comparingDouble(maxHeapNode::getCount).reversed();
        PriorityQueue<maxHeapNode> maxHeap = new PriorityQueue<>(comparator);
        boolean started = true;

        Set<String> everything = new HashSet<>();

        // Records the first word's hyponyms into the everything set, and after that,
        // only retains hyponyms that are the same with the every word that follows.
        for(String word : words){
            if (started) {
                everything.addAll(wNet.getEverything(word));
                started = false;
            } else {
                everything.retainAll(wNet.getEverything(word));
            }
        }
        System.out.println("K is " + k);
        // if k is less than zero, we return everything in the set.
        // else we return the kth item in the set ordered from most
        // used words between the startYear and endYear.
        if (k <= 0){
            List<String> everythingList = new ArrayList<>(everything);
            Collections.sort(everythingList);
            return everythingList.toString();
        } else { // k >= 2
            List<String> everythingList = new ArrayList<>();
            for (String hyponym : everything){
                double count = findWordOccurances(hyponym, startYear, endYear);
                if (count > 0){
                    maxHeap.add(new maxHeapNode(hyponym, count));
                }
            }
            for (int i = 0; i < k; i++){
                maxHeapNode maxWord = maxHeap.poll();
                if (maxWord == null){
                    break;
                }
                String word = maxWord.getWord();
                everythingList.add(word);
            }
            Collections.sort(everythingList);
            return everythingList.toString();
        }
    }

    /** Finds how many times "word" occurs in the specified timeframe */
    private double findWordOccurances(String word, int startYear, int endYear){
        TimeSeries ts = ngm.countHistory(word, startYear, endYear);
        if (ts == null){
            return 0;
        }
        List<Double> countsList = ts.data();
        double total = 0;
        for (Double d : countsList){
            total += d;
        }
        return total;
    }

}

