package com.refectoring.effectivejava.ref._06_mutable_data._05_combine_functions_into_transform;

import java.time.Month;
import java.time.Year;

public class Client1 extends ReadingClient {

    double baseCharge;

    public Client1(Reading reading) {
        this.baseCharge = enrichReading(reading).baseCharge();
    }

    public double getBaseCharge() {
        return baseCharge;
    }
}
