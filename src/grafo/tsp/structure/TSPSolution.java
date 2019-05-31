package grafo.tsp.structure;

import grafo.optilib.structure.Solution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TSPSolution implements Solution {

    private TSPInstance instance;
    private List<Integer> solution;
    private int totalDistance;

    public TSPSolution(TSPInstance instance) {
        this.instance = instance;
        solution = new ArrayList<>();
//        solution = new LinkedList<>();
        solution.add(1);
    }

    public void addNode(int node) {
        totalDistance -= instance.distance(solution.get(solution.size()-1), 1);
        totalDistance += instance.distance(solution.get(solution.size()-1), node);
        totalDistance += instance.distance(node, 1);
        solution.add(node);
    }

    public void swap(int posSrc, int posDst) {
        int posMin = Math.min(posSrc, posDst);
        int posMax = Math.max(posSrc, posDst);
        if (posMax-posMin == 1) {
            // Consecutive nodes
            int prevFirst = solution.get(posMin - 1);
            int nextSecond = (posMax == solution.size() - 1) ? 1 : solution.get(posMax+1);
            int nodeFirst = solution.get(posMin);
            int nodeSecond = solution.get(posMax);
            totalDistance -= (instance.distance(prevFirst, nodeFirst) + instance.distance(nodeSecond, nextSecond));
            totalDistance += (instance.distance(prevFirst, nodeSecond) + instance.distance(nodeFirst, nextSecond));
        } else {
            // Non-consecutive nodes
            int prevSrc = solution.get(posSrc - 1);
            int nextSrc = (posSrc == solution.size() - 1) ? 1 : solution.get(posSrc + 1);
            int nodeSrc = solution.get(posSrc);
            int prevDst = solution.get(posDst - 1);
            int nextDst = (posDst == solution.size() - 1) ? 1 : solution.get(posDst + 1);
            int nodeDst = solution.get(posDst);
            totalDistance -= (instance.distance(prevSrc, nodeSrc) + instance.distance(nodeSrc, nextSrc));
            totalDistance -= (instance.distance(prevDst, nodeDst) + instance.distance(nodeDst, nextDst));
            totalDistance += (instance.distance(prevSrc, nodeDst) + instance.distance(nodeSrc, nextDst));
            totalDistance += (instance.distance(prevDst, nodeSrc) + instance.distance(nodeDst, nextSrc));
        }
        int nodeSrc = solution.get(posSrc);
        int nodeDst = solution.get(posDst);
        solution.set(posSrc, nodeDst);
        solution.set(posDst, nodeSrc);
    }

    public void insert(int posSrc, int posDst) {
        if (Math.abs(posSrc - posDst) == 1) {
            swap(posSrc, posDst);
        } else {
            int node = solution.get(posSrc);
            // Old position
            int prev = solution.get(posSrc - 1);
            int next = (posSrc == solution.size() - 1) ? 1 : solution.get(posSrc + 1);
            int old = solution.get(posSrc);
            totalDistance -= (instance.distance(prev, old) + instance.distance(old, next));
            totalDistance += instance.distance(prev, next);

            // New position
//            int prevNew = solution.get(posDst - 1);
//            int nextNew = (posDst == solution.size()-1) ? 1 : solution.get(posDst+1);
            int prevNew;
            int nextNew;
            if (posSrc < posDst) {
                prevNew = solution.get(posDst);
                nextNew = (posDst == solution.size()-1) ? 1 : solution.get(posDst+1);
            } else {
                prevNew = solution.get(posDst-1);
                nextNew = solution.get(posDst);
            }
            totalDistance -= instance.distance(prevNew, nextNew);
            totalDistance += (instance.distance(prevNew, node) + instance.distance(node, nextNew));


            // Perform move
            solution.remove(posSrc);
//            if (posSrc < posDst) {
//                posDst--;
//            }
            if (posDst == solution.size()) {
                solution.add(node);
            } else {
                solution.add(posDst, node);
            }
        }
    }

    public int getNodeAt(int pos) {
        return solution.get(pos);
    }

    public boolean contains(int node) {
        return solution.contains(node);
    }

    public int eval() {
        if (solution.size() == 1) return Integer.MAX_VALUE;
        int totalDist = 0;
        for (int i = 1; i < solution.size(); i++) {
            totalDist += instance.distance(solution.get(i-1), solution.get(i));
        }
        totalDist += instance.distance(solution.get(solution.size()-1), 1);
        return totalDist;
    }

    public boolean isFeasible() {
        return solution.size() == instance.getN();
    }

    public TSPInstance getInstance() {
        return instance;
    }

    public int getTotalDistance() {
//        return eval();
        return totalDistance;
    }

    @Override
    public String toString() {
        return solution.toString();
    }
}
