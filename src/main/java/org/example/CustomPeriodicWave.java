package org.example;

import java.util.function.DoubleUnaryOperator;

public class CustomPeriodicWave extends SoundWave {
    private final DoubleUnaryOperator function;   // function that will be used to calculate values

    public CustomPeriodicWave(double frequency, double amplitude, double phase, String id, DoubleUnaryOperator func) {
        super(frequency, amplitude, phase, id);
        this.function = func;
    }

    private double periodicFunction(double t) {
        if(t < -0.5 - floatTolerance || t >= 0.5 + floatTolerance) throw new IllegalArgumentException("Illegal argument: values for a wave's base function must be between -0.5, inclusive and 0.5, exclusive, got " + t);
        return function.applyAsDouble(t);
    }

    public double returnCurrentValue(double t) {
        // this is for easier computation, since if f is a periodic function with period P,
        // f(x) = f(x + αP)
        // (α being a real number)
        System.out.println("t = " + t);
        return amplitude * periodicFunction(trueModulo(frequency * t + phase, this.getPeriod()));
    }

    @Override
    public String toString() {
        return id + " CUSTOM " + super.toString();
    }
}
