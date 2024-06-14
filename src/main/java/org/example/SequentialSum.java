package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SequentialSum {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("data.txt"));
        int[] data = lines.stream().mapToInt(Integer::parseInt).toArray();

        long startTime = System.nanoTime();
        long sum = 0;
        for (int num : data) {
            sum += num;
        }
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;

        System.out.println("Suma total: " + sum);
        System.out.println("Tiempo de ejecución secuencial: " + duration + " segundos");
    }
}
