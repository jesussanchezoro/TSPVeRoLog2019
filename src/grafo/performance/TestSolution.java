package grafo.performance;

import grafo.optilib.tools.RandomManager;
import grafo.tsp.structure.TSPInstance;
import grafo.tsp.structure.TSPSolution;
import grafo.tsp.structure.TSPSolutionEfficient;

import java.util.Random;
import java.util.Timer;

public class TestSolution {

    private final static int NUM_MOVES = 10000000;

    public static void main(String[] args) {
        RandomManager.setSeed(1234);
        Random rnd = RandomManager.getRandom();
//        String path = "small.txt";
//        String path = "/Users/jesus.sanchezoro/IdeaProjects/instancias/tsrp/all/a280_gen1.txt";
        String path = "/Users/jesus.sanchezoro/IdeaProjects/instancias/tsrp/all/u724_gen1.txt";
        TSPInstance instance = new TSPInstance(path);
//        TSPSolution sol = new TSPSolution(instance);
        TSPSolutionEfficient sol = new TSPSolutionEfficient(instance);
        int n = instance.getN();
        for (int i = 2; i <= n; i++) {
            sol.addNode(i);
        }
        long timeIni = System.currentTimeMillis();
        for (int i = 0; i < NUM_MOVES; i++) {
            int v1 = rnd.nextInt(n-2)+2;
            int v2 = rnd.nextInt(n-2)+2;
            while (v1 == v2) {
                v2 = rnd.nextInt(n-2)+2;
            }
//            sol.swap(v1, v2);
            sol.insert(v1, v2);
        }
        double secs = (System.currentTimeMillis() - timeIni) / 1000.0;
        System.out.println(sol);
        System.out.println(sol.getTotalDistance()+"\t"+sol.eval());
        System.out.println("Time: "+secs);
    }
}
