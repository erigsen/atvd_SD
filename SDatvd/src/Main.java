//Eric Lisboa Queiroz


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        String inputFile = "Entrada01.txt";
        String outputFileNameSequential = "output_sequential.txt";
        String outputFileNameParallel5 = "output_parallel_5.txt";
        String outputFileNameParallel10 = "output_parallel_10.txt";

        List<Integer> numbers = readNumbersFromFile(inputFile);

        long startTime = System.currentTimeMillis();
        List<Integer> primesSequential = PrimeCheckerSequential.findPrimes(numbers);
        long endTime = System.currentTimeMillis();
        long sequentialTime = endTime - startTime;
        writePrimesToFile(primesSequential, outputFileNameSequential);
        System.out.println("Tempo sequencial: " + sequentialTime + "ms");

        startTime = System.currentTimeMillis();
        List<Integer> primesParallel5 = PrimeCheckerParallel.findPrimes(numbers, 5);
        endTime = System.currentTimeMillis();
        long parallelTime5 = endTime - startTime;
        writePrimesToFile(primesParallel5, outputFileNameParallel5);
        System.out.println("Tempo paralelo (5 threads): " + parallelTime5 + "ms");

        startTime = System.currentTimeMillis();
        List<Integer> primesParallel10 = PrimeCheckerParallel.findPrimes(numbers, 10);
        endTime = System.currentTimeMillis();
        long parallelTime10 = endTime - startTime;
        writePrimesToFile(primesParallel10, outputFileNameParallel10);
        System.out.println("Tempo paralelo (10 threads): " + parallelTime10 + "ms");

        System.out.println("Tempo sequencial: " + sequentialTime + "ms");
        System.out.println("Tempo paralelo (5 threads): " + parallelTime5 + "ms");
        System.out.println("Tempo paralelo (10 threads): " + parallelTime10 + "ms");
    }

    private static List<Integer> readNumbersFromFile(String fileName) {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                numbers.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    private static void writePrimesToFile(List<Integer> primes, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (int prime : primes) {
                bw.write(prime + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
