import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PrimeCheckerParallel {
    public static List<Integer> findPrimes(List<Integer> numbers, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<List<Integer>>> futures = new ArrayList<>();

        int chunkSize = numbers.size() / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int fromIndex = i * chunkSize;
            int toIndex = (i == numThreads - 1) ? numbers.size() : (i + 1) * chunkSize;
            List<Integer> sublist = numbers.subList(fromIndex, toIndex);
            Future<List<Integer>> future = executor.submit(new PrimeTask(sublist));
            futures.add(future);
        }

        List<Integer> primes = new ArrayList<>();
        for (Future<List<Integer>> future : futures) {
            try {
                primes.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        return primes;
    }

    private static class PrimeTask implements Callable<List<Integer>> {
        private final List<Integer> numbers;

        public PrimeTask(List<Integer> numbers) {
            this.numbers = numbers;
        }

        @Override
        public List<Integer> call() {
            List<Integer> primes = new ArrayList<>();
            for (int num : numbers) {
                if (isPrime(num)) {
                    primes.add(num);
                }
            }
            return primes;
        }

        private boolean isPrime(int num) {
            if (num <= 1) {
                return false;
            }
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
