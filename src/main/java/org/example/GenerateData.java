package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateData {
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("data.txt")) {
            Random rand = new Random();
            for (int i = 0; i < 1000000; i++) {
                writer.write(rand.nextInt(10000) + 1 + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
