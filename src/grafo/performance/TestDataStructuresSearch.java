package grafo.performance;

import java.util.*;

public class TestDataStructuresSearch {

    private final static int N = 1000;
    private final static int SEARCH = 1000;
    private final static int ITERS = 10000;

    public static void main(String[] args) {
        Random rnd = new Random(1234);

//        List<Integer> elements = new ArrayList<>();
//        List<Integer> elements = new LinkedList<>();
//        Set<Integer> elements = new HashSet<>();
        int[] elements = new int[N];
        for (int i = 0; i < N; i++) {
            elements[i] = i;
//            elements.add(i);
        }

        long timeIni = System.currentTimeMillis();
        for (int i = 0; i < ITERS; i++) {
            for (int j = 0; j < SEARCH; j++) {
                int search = rnd.nextInt(N);
//                elements.contains(search);
                arraySearch(elements, search);
            }
        }
        double secs = (System.currentTimeMillis() - timeIni) / 1000.0;
        System.out.println("SEARCH: "+secs);
    }

    public static void arraySearch(int[] elements, int search) {
        for (int k = 0; k < N; k++) {
            if (elements[k] == search) {
                break;
            }
        }
    }

}
