package grafo.tsp.improvement;

import grafo.optilib.metaheuristics.Improvement;
import grafo.optilib.tools.RandomManager;
import grafo.tsp.structure.TSPInstance;
import grafo.tsp.structure.TSPSolution;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LSSwap implements Improvement<TSPSolution> {


    @Override
    public void improve(TSPSolution sol) {
        TSPInstance instance = sol.getInstance();
        int n = instance.getN();
        List<Integer> positions = IntStream.range(1,n).boxed().collect(Collectors.toList());
        boolean improve = true;
        while (improve) {
            improve = tryImprove(sol, positions);
        }
    }

    public boolean tryImprove(TSPSolution sol, List<Integer> positions) {
        Random rnd = RandomManager.getRandom();
        Collections.shuffle(positions, rnd);
        for (int i = 0; i < positions.size(); i++) {
            int posI = positions.get(i);
            for (int j = i+1; j < positions.size(); j++) {
                int posJ = positions.get(j);
                int prevOf = sol.getTotalDistance();
                sol.swap(posI, posJ);
                if (sol.getTotalDistance() < prevOf) {
                    return true;
                }
                sol.swap(posI, posJ);
            }
        }
        return false;
    }
}
