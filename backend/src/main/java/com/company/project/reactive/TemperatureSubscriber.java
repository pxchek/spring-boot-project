package com.company.project.reactive;

import java.util.concurrent.Flow;

public class TemperatureSubscriber implements Flow.Subscriber<TemperatureInfo> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);

    }

    @Override
    public void onNext(TemperatureInfo temperatureInfo) {
        System.out.println(temperatureInfo);
        subscription.request(1);

    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());

    }

    @Override
    public void onComplete() {
        System.out.println("Done!");

    }
}
