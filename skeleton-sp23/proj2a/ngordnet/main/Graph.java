package ngordnet.main;
import java.util.*;

public class Graph<T> {

    private Map<T, Set<T>> adj_map;

    public Graph(){
        adj_map = new HashMap<>();
    }

    /** Created a node inside of graph if the item is not already in it */
    public void addNode(T item){
        if (!adj_map.containsKey(item)){
            adj_map.put(item, new HashSet<>());
        }
    }

    /** Adds an edge from item1 TO item2 or item2 becomes item1's neighbor
     * But item2 does not become connected to item 1*/
    public void addDirectedEdge(T item1, T item2){
        if (!adj_map.containsKey(item1)){
            addNode(item1);
        }
        if (!adj_map.containsKey(item2)){
            addNode(item2);
        }
        Set<T> adj_list = adj_map.get(item1);
        adj_list.add(item2);
    }

    /** returns a set of a Node's neighbors */
    public Set<T> getNeighbors(T item){
        return adj_map.get(item);
    }
}
