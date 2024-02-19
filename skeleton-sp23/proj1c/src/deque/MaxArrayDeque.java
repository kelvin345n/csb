package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> c;
    public MaxArrayDeque(Comparator<T> c){
        super();
        this.c = c;
    }

    private T maxHelper(Comparator<T> c){
        T maximum = get(0);
        for (int i = 1; i < size(); i++){
            T comparison = get(i);
            if (c.compare(maximum, comparison) < 0){
                maximum = comparison;
            }
        }
        return maximum;
    }

    public T max(){
        return maxHelper(this.c);
    }

    public T max(Comparator<T> c){
        return maxHelper(c);
    }
}
