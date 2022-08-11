package com.company.project.reactive;

import java.util.Random;
import java.util.StringJoiner;

public class TemperatureInfo {
    public static final Random random = new Random();
    public static int maxTemperatureReadings = 10;

    private final String country;
    private final int temperature;

    public TemperatureInfo(String country, int temperature) {
        this.country = country;
        this.temperature = temperature;
        maxTemperatureReadings--;
    }

    public static TemperatureInfo fetch(String country) throws Exception {
        while(maxTemperatureReadings > 0) {
            return new TemperatureInfo(country, random.nextInt(100));
        }

        throw new Exception("Error!");
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TemperatureInfo.class.getSimpleName() + "[", "]")
                .add("country='" + country + "'")
                .add("temperature=" + temperature)
                .toString();
    }

    public String getCountry() {
        return country;
    }

    public int getTemperature() {
        return temperature;
    }
}
