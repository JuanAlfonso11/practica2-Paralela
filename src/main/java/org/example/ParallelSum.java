package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ParallelSum {
    static class SumTask extends RecursiveTask<Long> {
        private final int[] data;
        private final int start, end;
        private static final int THRESHOLD = 10000;

        SumTask(int[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += data[i];
                }
                return sum;
            } else {
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(data, start, mid);
                SumTask rightTask = new SumTask(data, mid, end);
                leftTask.fork();
                return rightTask.compute() + leftTask.join();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("data.txt"));
        int[] data = lines.stream().mapToInt(Integer::parseInt).toArray();

        int[] numThreads = {2, 4, 8, 16, 32};
        for (int threads : numThreads) {
            ForkJoinPool pool = new ForkJoinPool(threads);
            long startTime = System.nanoTime();
            long sum = pool.invoke(new SumTask(data, 0, data.length));
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1_000_000_000.0;

            System.out.println("Número de hilos: " + threads);
            System.out.println("Suma total: " + sum);
            System.out.println("Tiempo de ejecución paralelo: " + duration + " segundos");
            System.out.println();
        }
    }
}
