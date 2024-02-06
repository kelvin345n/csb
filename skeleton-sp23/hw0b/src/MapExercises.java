import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {

        Map<Character, Integer> map_abc = new HashMap<>();
        int count = 1;
        for (char x = 'a'; x <= 'z'; x++){
            map_abc.put(x, count);
            count++;
        }

        return map_abc;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> squares_map= new HashMap<>();
        for (int get : nums){
            squares_map.put(get, get*get);
        }
        return squares_map;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> count_words_map = new HashMap<>();
        for (String s : words){
            if (count_words_map.containsKey(s)){
                int g = count_words_map.get(s);
                g += 1;
                count_words_map.put(s, g);
            } else {
                count_words_map.put(s, 1);
            }
        }

        return count_words_map;
    }
}
