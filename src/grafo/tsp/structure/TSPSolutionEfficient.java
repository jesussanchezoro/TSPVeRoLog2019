package grafo.tsp.structure;

import grafo.optilib.structure.Solution;

import java.util.LinkedList;
import java.util.List;

public class TSPSolutionEfficient implements Solution {

    private TSPInstance instance;
    private MyLinkedList solution;

    public TSPSolutionEfficient(TSPInstance instance) {
        this.instance = instance;
        solution = new MyLinkedList(instance);
    }

    public void addNode(int node) {
        solution.add(node);
    }

    public void swap(int node1, int node2) {
        solution.swap(node1, node2);
    }

    public void insert(int node1, int node2) {
        solution.insert(node1, node2);
    }

    public int getNext(int node) {
        return solution.next(node);
    }

    public int getPrev(int node) {
        return solution.prev(node);
    }

    public TSPInstance getInstance() {
        return instance;
    }

    public int eval() {
        return solution.eval();
    }

    public int getTotalDistance() {
        return solution.getTotalDistance();
    }

    @Override
    public String toString() {
        return solution.toString();
    }
}
