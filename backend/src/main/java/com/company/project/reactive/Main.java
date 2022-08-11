package com.company.project.reactive;

import java.util.concurrent.Flow;

public class Main {
    public static void main(String[] args) {
        getTemperatures("India").subscribe(new TemperatureSubscriber());
    }

    private static Flow.Publisher<TemperatureInfo> getTemperatures(String country) {
        return subscriber -> subscriber.onSubscribe(new TemperatureSubscription(subscriber, country));
    }
}
