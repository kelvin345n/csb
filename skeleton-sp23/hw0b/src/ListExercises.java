import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int sum = 0;
        for (int i = 0; i < L.size(); i++){
            sum += L.get(i);
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> even_list = new ArrayList<>();
        for (int i : L){
            if (i % 2 == 0){
                even_list.add(i);
            }
        }
        return even_list;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> common_items = new ArrayList<>();
        for (int i : L1){
            for (int j : L2){
                if (i == j){
                    common_items.add(i);
                }
            }
        }
        return common_items;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int count = 0;
        for (String s : words){
            char[] parts = s.toCharArray();
            for (char i : parts){
                if (c == i){
                    count += 1;
                }
            }
        }
        return count;
    }
}
