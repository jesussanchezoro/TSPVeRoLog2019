package grafo.tsp.constructives;

import grafo.optilib.metaheuristics.Constructive;
import grafo.optilib.tools.RandomManager;
import grafo.tsp.structure.TSPInstance;
import grafo.tsp.structure.TSPSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GRASP implements Constructive<TSPInstance, TSPSolution> {

    private class Candidate {
        int v;
        int cost;

        public Candidate(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }
    }

    private float alpha;

    public GRASP(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public TSPSolution constructSolution(TSPInstance instance) {
        Random rnd = RandomManager.getRandom();
        float realAlpha = (alpha >= 0) ? alpha : rnd.nextFloat();
        TSPSolution sol = new TSPSolution(instance);
        int n = instance.getN();
        int first = rnd.nextInt(n-2)+2;
        sol.addNode(first);
        List<Candidate> cl = createCandidateList(sol, first);
        while (cl.size() > 1) {
            int gmin = cl.get(0).cost;
            int gmax = cl.get(cl.size()-1).cost;
            float th = gmin + realAlpha * (gmax-gmin);
            List<Candidate> rcl = new ArrayList<>(cl.size());
            for (Candidate c : cl) {
                if (c.cost <= th) {
                    rcl.add(c);
                }
            }
            Candidate selected = rcl.get(rnd.nextInt(rcl.size()));
            cl.remove(selected);
            sol.addNode(selected.v);
            updateCandidateList(sol, cl, selected.v);
        }
        sol.addNode(cl.get(0).v);
        return sol;
    }

    private List<Candidate> createCandidateList(TSPSolution sol, int first) {
        TSPInstance instance = sol.getInstance();
        int n = instance.getN();
        List<Candidate> cl = new ArrayList<>(n);
        for (int node = 2; node <= n; node++) {
            if (node == first) continue;
            int dist = instance.distance(node, first);
            cl.add(new Candidate(node, dist));
        }
        cl.sort(Comparator.comparingInt(c -> c.cost));
        return cl;
    }

    private void updateCandidateList(TSPSolution sol, List<Candidate> cl, int selected) {
        TSPInstance instance = sol.getInstance();
        for (Candidate c : cl) {
            c.cost = instance.distance(c.v, selected);
        }
        cl.sort(Comparator.comparingInt(c -> c.cost));
    }
}
