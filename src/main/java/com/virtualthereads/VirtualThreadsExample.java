package com.virtualthereads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class VirtualThreadsExample {

    public static void main(String[] args) {
        // Create ExecutorService with Virtual Threads
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            // Number of requests to send
            int numberOfRequests = 10;

            // Submit requests concurrently
            Future<Void>[] futures = new Future[numberOfRequests];
            for (int i = 0; i < numberOfRequests; i++) {
                final int requestId = i;
                futures[i] = executorService.submit(() -> {
                    handleRequest(requestId);
                    return null;
                });
            }

            // Wait for all requests to complete
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("All requests processed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(int requestId) {
        try {
            // Simulate request processing time
            int processingTime = ThreadLocalRandom.current().nextInt(1000, 3000);
            TimeUnit.MILLISECONDS.sleep(processingTime);

            // Display request processing result
            System.out.printf("Request %d processed in %d ms%n", requestId, processingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.printf("Request %d interrupted%n", requestId);
        }
    }
}
