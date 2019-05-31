package grafo.tsp.test;

import grafo.optilib.metaheuristics.Algorithm;
import grafo.optilib.metaheuristics.Constructive;
import grafo.optilib.metaheuristics.Improvement;
import grafo.optilib.tools.RandomManager;
import grafo.optilib.tools.Timer;
import grafo.tsp.algorithms.AlgConstLS;
import grafo.tsp.algorithms.AlgConstLSBasic;
import grafo.tsp.algorithms.AlgConstLSParallel;
import grafo.tsp.constructives.GRASP;
import grafo.tsp.constructives.GRASPBetter;
import grafo.tsp.constructives.GRASPBetterEfficient;
import grafo.tsp.improvement.LSInsert;
import grafo.tsp.improvement.LSInsertEfficient;
import grafo.tsp.improvement.LSSwap;
import grafo.tsp.improvement.LSSwapEfficient;
import grafo.tsp.structure.TSPInstance;
import grafo.tsp.structure.TSPSolution;
import grafo.tsp.structure.TSPSolutionEfficient;

public class TestAlgorithmEfficient {

    public static void main(String[] args) {
        RandomManager.setSeed(1234);
        // Instance Selection
//        String path = "small.txt";
//        String path = "/Users/jesus.sanchezoro/IdeaProjects/instancias/tsrp/all/rd100_gen1.txt";
//        String path = "/Users/jesus.sanchezoro/IdeaProjects/instancias/tsrp/all/a280_gen1.txt";
        String path = "instances/u724_gen1.txt";
        TSPInstance instance = new TSPInstance(path);

        // ALGORITHM
        int nConstructions = 10;
        // BASIC VARIANT
//        Constructive<TSPInstance, TSPSolution> c = new GRASP(-1f);
//        Constructive<TSPInstance, TSPSolution> c = new GRASPBetter(-1f);
//        Improvement<TSPSolution> ls = new LSSwap();
//        Improvement<TSPSolution> ls = new LSInsert();
//        Algorithm<TSPInstance> alg = new AlgConstLSBasic(c, ls, nConstructions);

        Constructive<TSPInstance, TSPSolutionEfficient> c = new GRASPBetterEfficient(-1f);
//        Improvement<TSPSolutionEfficient> ls = new LSSwapEfficient();
        Improvement<TSPSolutionEfficient> ls = new LSInsertEfficient();

//        Algorithm<TSPInstance> alg = new AlgConstLS(c, ls, nConstructions);
        Algorithm<TSPInstance> alg = new AlgConstLSParallel(c, ls, nConstructions);

        // Execution
        alg.execute(instance);
    }
}
