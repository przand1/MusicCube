package musiccube.recommendations;

import java.util.*;

import static java.util.Comparator.reverseOrder;

public class RecommendationsIdListBuilder {
    private RecommendationsIdListBuilder() {}


    public static List<Integer> build(List<Set<Integer>> idSets, int limit) {
        final HashMap<Integer,Integer> idsAndWeightsMap = new HashMap<>();
        idSets.forEach(set -> set.forEach(id -> {
            if (idsAndWeightsMap.containsKey(id)) {
                idsAndWeightsMap.replace(id,idsAndWeightsMap.get(id) + 1);
            } else {
                idsAndWeightsMap.put(id,1);
            }
        }));
        List<Integer> result = new ArrayList<>(sortByValue(idsAndWeightsMap).keySet());
        if (result.size() > limit) {
            result = result.subList(0,limit);
        }
        return result;
    }

    private static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
    {
        // Create a list from elements of HashMap 
        List<Map.Entry<Integer, Integer> > list =
                new LinkedList<>(hm.entrySet());

        // Sort the list 
        list.sort(Comparator.comparing(o -> (o.getValue())));
        Collections.reverse(list);

        // put data from sorted list to hashmap  
        HashMap<Integer, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
