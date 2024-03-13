package ngordnet.main;

import edu.princeton.cs.algs4.In;
import net.sf.saxon.expr.Component;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class WordNet {
    // Connects word ID's to other word ID's.
    // Each connection means that an ID is a hyponym of the parent
    private Graph<String> wordNetGraph;

    // Takes in ID and returns a list of the synonyms associated with
    // that string.
    private Map<String, String[]> synonymsMap;

    // Takes in a word and returns a list of ID's of that word.
    // Because a word can have multiple ID's
    private Map<String, List<String>> idMap;

    public WordNet(String synsetFile, String hyponymsFile){
        wordNetGraph = new Graph<>();
        synonymsMap = new HashMap<>();
        idMap = new HashMap<>();
        readSynsetFile(synsetFile);
        readHyponymFile(hyponymsFile);
        // Build graph by adding all the edges
        // by parsing out of hyponyms and synsets
    }
    /** Reads in the synsetFile and populates the synonymsMap */
    private void readSynsetFile(String synsetFile){
        In in = new In(synsetFile);

        while(in.hasNextLine()){
            String line = in.readLine();
            String[] parts = line.split(",");

            // parts should be split into a list of 3 items
            // parts[0] = String of the ID
            // parts[1] = String of word/synonyms (separated by a space)
            // parts[2] = String of the definition

            String wordID = parts[0];
            String synonyms = parts[1];
            // Definition is not needed

            String[] synonymsList = synonyms.split(" ");
            synonymsMap.put(wordID, synonymsList);

            for (String word : synonymsList){
                if (!idMap.containsKey(word)){
                    idMap.put(word, new ArrayList<>());
                }
                List<String> idList = idMap.get(word);
                idList.add(wordID);
            }
        }
    }

    /** Reads in hyponymFile and populates the wordNetGraph */
    private void readHyponymFile(String hyponymFile) {
        In in = new In(hyponymFile);

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split(",");

            // parts is a list of integers which corresponds
            // to the ID of a word
            // parts[0] is the parent ID. For every ID after index 0
            // parent word is connected to that ID.

            String parentID = parts[0];
            for (int i = 1; i < parts.length; i++){
                wordNetGraph.addDirectedEdge(parentID, parts[i]);
            }
        }
    }

    /** returns array of every synonym including the parent word */
    public List<String> getSynonyms(String parentWord){
        List<String> parentidList = idMap.get(parentWord);
        Set<String> synonymsArray = new HashSet<>();
        for (String id : parentidList){
            String[] synonyms = synonymsMap.get(id);
            for (String word : synonyms){
                synonymsArray.add(word);
            }
        }
        return new ArrayList<>(synonymsArray);
    }

    /** Returns a list of all the hyponyms of parentWord */
    public List<String> getHyponyms(String parentWord){
        Set<String> hyponymsSet = new HashSet<>();
        // List of ID's that the parent is associated with
        List<String> parentIDList = idMap.get(parentWord);
        if (parentIDList == null){
            return new ArrayList<>();
        }

        for (String id : parentIDList){
            hyponymsSet.addAll(getHyponymsHelper(id));
        }
        return new ArrayList<>(hyponymsSet);
    }

    private Set<String> getHyponymsHelper(String id){
        Set<String> hyponymsSet = new HashSet<>();
        Set<String> neighborsID = wordNetGraph.getNeighbors(id);

        for (String neighborID : neighborsID){
            String[] neighborSynonyms = synonymsMap.get(neighborID);
            hyponymsSet.addAll(List.of(neighborSynonyms));

            Set<String> neighborHyponyms = getHyponymsHelper(neighborID);
            hyponymsSet.addAll(neighborHyponyms);
        }
        return hyponymsSet;
    }

    /** Returns a sorted list of all the parentWord's synonyms (including itself)
     * and all the parentWord's hyponyms */
    public List<String> getEverything(String parentWord){
        Set<String> everything = new HashSet<>();
        if (idMap.containsKey(parentWord)){
            everything.addAll(getSynonyms(parentWord));
            everything.addAll(getHyponyms(parentWord));
        }
        List<String> everythingList = new ArrayList<>(everything);
        Collections.sort(everythingList);
        return everythingList;
    }
}
