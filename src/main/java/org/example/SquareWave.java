package org.example;

public class SquareWave extends CustomPeriodicWave {
    // static final wave function for a square wave
    private static final double squareWaveBaseFunction(double t) {
        if (t < 0) return -1; else return 1;
    }

    public SquareWave(double frequency, double amplitude, double phase, String id) {
        super (frequency, amplitude, phase, id, SquareWave::squareWaveBaseFunction);
    }

    @Override
    public String toString() {
        return id + " SQUARE WAVE: f = " + frequency + " Hz; A = " + amplitude + "; φ = " + phase;
    }
}
