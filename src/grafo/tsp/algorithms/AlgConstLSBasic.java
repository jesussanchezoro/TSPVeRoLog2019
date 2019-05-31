package grafo.tsp.algorithms;

import grafo.optilib.metaheuristics.Algorithm;
import grafo.optilib.metaheuristics.Constructive;
import grafo.optilib.metaheuristics.Improvement;
import grafo.optilib.results.Result;
import grafo.optilib.structure.Solution;
import grafo.optilib.tools.Timer;
import grafo.tsp.structure.TSPInstance;
import grafo.tsp.structure.TSPSolution;
import grafo.tsp.structure.TSPSolutionEfficient;

public class AlgConstLSBasic implements Algorithm<TSPInstance> {

    private Constructive<TSPInstance, TSPSolution> c;
    private Improvement<TSPSolution> ls;
    private int nConstructions;
    private TSPSolution best;

    public AlgConstLSBasic(Constructive<TSPInstance, TSPSolution> c, Improvement<TSPSolution> ls, int nConstructions) {
        this.c = c;
        this.ls = ls;
        this.nConstructions = nConstructions;
    }

    @Override
    public Result execute(TSPInstance instance) {
        Result r = new Result(instance.getName());
        System.out.println("Executing "+instance.getName());
        Timer.initTimer();
        for (int i = 0; i < nConstructions; i++) {
            TSPSolution sol = c.constructSolution(instance);
            ls.improve(sol);
            System.out.println("C"+i+": "+Timer.getTime()/1000.0);
            if (best == null || sol.getTotalDistance() < best.getTotalDistance()) {
                best = sol;
            }
        }
        double secs = Timer.getTime() / 1000.0;
        r.add("OF", best.getTotalDistance());
        r.add("Time (s)", secs);
        System.out.println("TOTAL TIME: "+secs);
        return null;
    }

    @Override
    public Solution getBestSolution() {
        return best;
    }
}
