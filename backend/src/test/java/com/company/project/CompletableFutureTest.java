package com.company.project;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {

    public void waitAllResponses() throws URISyntaxException, InterruptedException, ExecutionException {

        List<URI> uris = Arrays.asList(
                new URI("http://10.0.0.128:30007/1"),
                new URI("http://10.0.0.128:30007/2"),
                new URI("http://10.0.0.128:30007/3"),
                new URI("http://10.0.0.128:30007/4"),
                new URI("http://10.0.0.128:30007/5"),
                new URI("http://10.0.0.128:30007/6"),
                new URI("http://10.0.0.128:30007/7"),
                new URI("http://10.0.0.128:30007/8"),
                new URI("http://10.0.0.128:30007/9"),
                new URI("http://10.0.0.128:30007/10"));

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        HttpClient client = HttpClient
                .newBuilder()
                .executor(executorService)
                .build();

        List<HttpRequest> requests = uris
                .stream()
                .map(HttpRequest::newBuilder)
                .map(reqBuilder -> reqBuilder.build())
                .collect(Collectors.toList());

        CompletableFuture<String>[] arrayResponses = requests
                .stream()
                .map(req -> asyncResponse(client, req))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> responses = CompletableFuture.allOf(arrayResponses);

        while (!responses.isDone()) {
            Thread.sleep(50);
            System.out.println("Waiting for all responses and executing other tasks...");
        }

        responses.get(); // eventually, add a timeout

        List<String> results = responses.thenApply(e -> {

            List<String> bodies = new ArrayList<>();
            for (CompletableFuture<String> body : arrayResponses) {
                bodies.add(body.join());
            }

            return bodies;
        }).get();

        results.forEach(System.out::println);

        executorService.shutdown();
    }

    private static CompletableFuture<String> asyncResponse(HttpClient client, HttpRequest request) {

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply((res) -> res.uri() + " | " + res.body() + "\n")
                .exceptionally(e -> "Exception: " + e);
    }

    public static void main(String[] args) throws URISyntaxException, ExecutionException, InterruptedException {
        CompletableFutureTest responses = new CompletableFutureTest();
        responses.waitAllResponses();
    }
}