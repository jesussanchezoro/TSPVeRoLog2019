package grafo.tsp.structure;

public class MyLinkedList {

    private TSPInstance instance;
    private int[] prev;
    private int[] next;
    private int totalDistance;
    private int lastNode;

    public MyLinkedList(TSPInstance instance) {
        int n = instance.getN();
        this.instance = instance;
        prev = new int[n+1];
        next = new int[n+1];
        next[1] = 1;
        totalDistance = 0;
        lastNode = 1;
    }

    public int prev(int node) {
        return prev[node];
    }

    public int next(int node) {
        return next[node];
    }

    public void addAfter(int nodeBefore, int node) {
        int nextNodeBefore = (next[nodeBefore]==0) ? 1 : next[nodeBefore];
        totalDistance -= instance.distance(nodeBefore, nextNodeBefore);
        totalDistance += instance.distance(nodeBefore, node) + instance.distance(node, nextNodeBefore);
        prev[node] = nodeBefore;
        next[node] = next[nodeBefore];
        next[nodeBefore] = node;
        if (nextNodeBefore > 1) {
            prev[nextNodeBefore] = node;
        }
        lastNode = node;
    }

    public void add(int node) {
        addAfter(lastNode, node);
    }

    public void swap(int node1, int node2) {
        if (prev[node1] == node2 || prev[node2] == node1){
            int nodeFirst = prev[node1] == node2 ? node2 : node1;
            int nodeSecond = node1 == nodeFirst ? node2 : node1;
            int prevFirst = prev[nodeFirst];
            int nextSecond = next[nodeSecond];
            totalDistance -= (instance.distance(prevFirst, nodeFirst) + instance.distance(nodeSecond, nextSecond));
            totalDistance += (instance.distance(prevFirst, nodeSecond) + instance.distance(nodeFirst, nextSecond));

            next[prevFirst] = nodeSecond;
            if (nextSecond > 1) {
                prev[nextSecond] = nodeFirst;
            }
            next[nodeSecond] = nodeFirst;
            prev[nodeSecond] = prevFirst;
            prev[nodeFirst] = nodeSecond;
            next[nodeFirst] = nextSecond;
        } else {
            int prevNode1 = prev[node1];
            int nextNode1 = next[node1];
            int prevNode2 = prev[node2];
            int nextNode2 = next[node2];
            totalDistance -= (instance.distance(prevNode1, node1) + instance.distance(node1, nextNode1));
            totalDistance -= (instance.distance(prevNode2, node2) + instance.distance(node2, nextNode2));
            totalDistance += (instance.distance(prevNode1, node2) + instance.distance(node2, nextNode1));
            totalDistance += (instance.distance(prevNode2, node1) + instance.distance(node1, nextNode2));


            next[prevNode1] = node2;
            next[prevNode2] = node1;
            if (next[node1] > 1) {
                prev[nextNode1] = node2;
            }
            if (next[node2] > 1) {
                prev[nextNode2] = node1;
            }
            prev[node1] = prevNode2;
            prev[node2] = prevNode1;
            next[node1] = nextNode2;
            next[node2] = nextNode1;
        }
    }

    public void insert(int node1, int node2) {
        if (prev[node1] == node2 || prev[node2] == node1) {
            swap(node1, node2);
        } else {
            int prevNode1 = prev[node1];
            int nextNode1 = next[node1];
            int nextNode2 = next[node2];
            totalDistance -= (instance.distance(prevNode1, node1) + instance.distance(node1, nextNode1));
            totalDistance += instance.distance(prevNode1, nextNode1);
            totalDistance -= instance.distance(node2, nextNode2);
            totalDistance += (instance.distance(node2, node1) + instance.distance(node1, nextNode2));
            // Where node1 was
            next[prevNode1] = nextNode1;
            prev[nextNode1] = prevNode1;
            // Where node1 is now
            next[node1] = nextNode2;
            prev[node1] = node2;
            next[node2] = node1;
            if (nextNode2 > 1) {
                prev[nextNode2] = node1;
            }
        }
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public int eval() {
        int current = next[1];
        int distance = 0;
        while (current != 1) {
            distance += instance.distance(prev[current], current);
            if (next[current] == 1) {
                distance += instance.distance(current, 1);
            }
            current = next[current];
        }
        return distance;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        int current = 1;
        while (current != 0) {
            stb.append(current).append(" ");
            current = next[current];
            if (current == 1) current = 0;
        }
        return stb.toString();
    }
}
