package grafo.tsp.algorithms;

import grafo.optilib.metaheuristics.Algorithm;
import grafo.optilib.metaheuristics.Constructive;
import grafo.optilib.metaheuristics.Improvement;
import grafo.optilib.results.Result;
import grafo.optilib.structure.Solution;
import grafo.optilib.tools.Timer;
import grafo.tsp.structure.TSPInstance;
import grafo.tsp.structure.TSPSolutionEfficient;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlgConstLSParallel implements Algorithm<TSPInstance> {

    private Constructive<TSPInstance, TSPSolutionEfficient> c;
    private Improvement<TSPSolutionEfficient> ls;
    private int nConstructions;
    private TSPSolutionEfficient best;

    public AlgConstLSParallel(Constructive<TSPInstance, TSPSolutionEfficient> c, Improvement<TSPSolutionEfficient> ls, int nConstructions) {
        this.c = c;
        this.ls = ls;
        this.nConstructions = nConstructions;
    }

    @Override
    public Result execute(TSPInstance instance) {
        Result r = new Result(instance.getName());
        System.out.println("Executing "+instance.getName());
        CountDownLatch latch = new CountDownLatch(nConstructions);
        Lock lock = new ReentrantLock();
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Timer.initTimer();
        for (int i = 0; i < nConstructions; i++) {
            int finalI = i;
            pool.submit(() -> {
                TSPSolutionEfficient sol = c.constructSolution(instance);
                ls.improve(sol);
                lock.lock();
                if (best == null || sol.getTotalDistance() < best.getTotalDistance()) {
                    best = sol;
                }
                lock.unlock();
                System.out.println("C"+finalI+": "+Timer.getTime()/1000.0);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double secs = Timer.getTime() / 1000.0;
        r.add("OF", best.getTotalDistance());
        r.add("Time (s)", secs);
        System.out.println("TOTAL TIME: "+secs);
        pool.shutdown();
        try {
            pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Solution getBestSolution() {
        return best;
    }
}
