package com.company.project.reactive;

import java.util.concurrent.Flow;

public class TemperatureSubscription implements Flow.Subscription {

    private final Flow.Subscriber<? super TemperatureInfo> subscriber;
    private final String country;

    public TemperatureSubscription(Flow.Subscriber<? super TemperatureInfo> subscriber, String country) {
        this.subscriber = subscriber;
        this.country = country;
    }

    @Override
    public void request(long n) {
        for (long i = 0L; i < n; i++) {
            try {
                subscriber.onNext(TemperatureInfo.fetch(country));
            } catch (Exception e) {
                subscriber.onError(e);
                break;
            }
        }

    }

    @Override
    public void cancel() {
        subscriber.onComplete();
    }
}
