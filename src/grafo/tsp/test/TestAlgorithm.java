package grafo.tsp.test;

import grafo.optilib.metaheuristics.Algorithm;
import grafo.optilib.metaheuristics.Constructive;
import grafo.optilib.metaheuristics.Improvement;
import grafo.optilib.tools.RandomManager;
import grafo.tsp.algorithms.AlgConstLSBasic;
import grafo.tsp.constructives.GRASP;
import grafo.tsp.improvement.LSSwap;
import grafo.tsp.structure.TSPInstance;
import grafo.tsp.structure.TSPSolution;

public class TestAlgorithm {

    public static void main(String[] args) {
        RandomManager.setSeed(1234);
        // Instance Selection
//        String path = "instances/rd100_gen1.txt";
        String path = "instances/a280_gen1.txt";
//        String path = "instances/u724_gen1.txt";
        TSPInstance instance = new TSPInstance(path);

        // ALGORITHM
        int nConstructions = 10;
        // BASIC VARIANT
//        Constructive<TSPInstance, TSPSolution> c = new GRASP(-1f);
        Constructive<TSPInstance, TSPSolution> c = new GRASP(-1f);
        Improvement<TSPSolution> ls = new LSSwap();
//        Improvement<TSPSolution> ls = new LSInsert();
        Algorithm<TSPInstance> alg = new AlgConstLSBasic(c, ls, nConstructions);

        // Execution
        alg.execute(instance);
    }
}
