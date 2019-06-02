package grafo.tsp.test;

import grafo.optilib.metaheuristics.Algorithm;
import grafo.optilib.metaheuristics.Constructive;
import grafo.optilib.metaheuristics.Improvement;
import grafo.optilib.tools.RandomManager;
import grafo.tsp.algorithms.AlgConstLS;
import grafo.tsp.algorithms.AlgConstLSBasic;
import grafo.tsp.algorithms.AlgConstLSParallel;
import grafo.tsp.constructives.GRASP;
import grafo.tsp.constructives.GRASPEfficient;
import grafo.tsp.improvement.LSSwap;
import grafo.tsp.improvement.LSSwapEfficient;
import grafo.tsp.structure.TSPInstance;
import grafo.tsp.structure.TSPSolution;
import grafo.tsp.structure.TSPSolutionEfficient;

public class TestAlgorithmEfficient {

    public static void main(String[] args) {
        RandomManager.setSeed(1234);
        // Instance Selection
//        String path = "instances/rd100_gen1.txt";
//        String path = "instances/a280_gen1.txt";
        String path = "instances/u724_gen1.txt";
        TSPInstance instance = new TSPInstance(path);

        // ALGORITHM
        int nConstructions = 10;

        // EFFICIENT
        Constructive<TSPInstance, TSPSolutionEfficient> c = new GRASPEfficient(-1f);
        Improvement<TSPSolutionEfficient> ls = new LSSwapEfficient();
//        Improvement<TSPSolutionEfficient> ls = new LSInsertEfficient();

//        Algorithm<TSPInstance> alg = new AlgConstLS(c, ls, nConstructions);
        Algorithm<TSPInstance> alg = new AlgConstLSParallel(c, ls, nConstructions);

        // Execution
        alg.execute(instance);
    }
}
