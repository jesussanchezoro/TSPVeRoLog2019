package grafo.tsp.structure;

import grafo.optilib.structure.Instance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TSPInstance implements Instance {

    private String name;
    private int n;
    private int[][] distances;

    public TSPInstance(String path) {
        readInstance(path);
    }

    @Override
    public void readInstance(String path) {
        name = path.substring(path.lastIndexOf('/')+1);
        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            n = Integer.parseInt(bf.readLine());
            distances = new int[n+1][n+1];
            String[] tokens;
            for (int i = 1; i <= n; i++) {
                for (int j = i+1; j <= n; j++) {
                    tokens = bf.readLine().split(" ");
                    int v1 = Integer.parseInt(tokens[0]);
                    int v2 = Integer.parseInt(tokens[1]);
                    int d = Integer.parseInt(tokens[2]);
                    distances[v1][v2] = d;
                    distances[v2][v1] = d;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int distance(int v1, int v2) {
        return distances[v1][v2];
    }

    public int getN() {
        return n;
    }

    public String getName() {
        return name;
    }
}
