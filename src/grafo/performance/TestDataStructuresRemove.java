package grafo.performance;

import java.util.*;

public class TestDataStructuresRemove {

    private final static int N = 200000;

    public static void main(String[] args) {
        Random rnd = new Random(1234);

//        List<Integer> elements = new ArrayList<>();
        List<Integer> elements = new LinkedList<>();
//        Set<Integer> elements = new HashSet<>();
        for (int i = 0; i < N; i++) {
            elements.add(i);
        }

        long timeIni = System.currentTimeMillis();
        for (int k = 0; k < N; k++) {
//            elements.remove(0);
//            elements.remove(elements.size()-1);
//            elements.remove(Integer.valueOf(k));
            elements.remove(Integer.valueOf(N-k-1));
        }
        double secs = (System.currentTimeMillis() - timeIni) / 1000.0;
        System.out.println("REMOVE: "+secs);
    }

}
