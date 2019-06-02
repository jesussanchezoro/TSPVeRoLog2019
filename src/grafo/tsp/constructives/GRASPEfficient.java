package grafo.tsp.constructives;

import grafo.optilib.metaheuristics.Constructive;
import grafo.optilib.tools.RandomManager;
import grafo.tsp.structure.TSPInstance;
import grafo.tsp.structure.TSPSolution;
import grafo.tsp.structure.TSPSolutionEfficient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GRASPEfficient implements Constructive<TSPInstance, TSPSolutionEfficient> {

    private class Candidate {
        int v;
        int cost;

        public Candidate(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }
    }

    private float alpha;

    public GRASPEfficient(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public TSPSolutionEfficient constructSolution(TSPInstance instance) {
        Random rnd = new Random(RandomManager.getRandom().nextInt(1000));
        float realAlpha = (alpha >= 0) ? alpha : rnd.nextFloat();
        TSPSolutionEfficient sol = new TSPSolutionEfficient(instance);
        int n = instance.getN();
        int first = rnd.nextInt(n-2)+2;
        sol.addNode(first);
        List<Candidate> cl = createCandidateList(sol, first);
        while (cl.size() > 1) {
            int gmin = cl.get(0).cost;
            int gmax = cl.get(cl.size()-1).cost;
            float th = gmin + realAlpha * (gmax-gmin);
            int limit = 0;
            while (limit < cl.size() && cl.get(limit).cost <= th) {
                limit++;
            }
            Candidate selected = cl.remove(rnd.nextInt(limit));
            sol.addNode(selected.v);
            updateCandidateList(sol, cl, selected.v);
        }
        sol.addNode(cl.get(0).v);
        return sol;
    }

    private List<Candidate> createCandidateList(TSPSolutionEfficient sol, int first) {
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

    private void updateCandidateList(TSPSolutionEfficient sol, List<Candidate> cl, int selected) {
        TSPInstance instance = sol.getInstance();
        int n = instance.getN();
        for (Candidate c : cl) {
            c.cost = instance.distance(c.v, selected);
        }
        cl.sort(Comparator.comparingInt(c -> c.cost));
    }
}
