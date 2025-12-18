package org.example;

public class TriangleWave extends CustomPeriodicWave {
    // static final wave function for a triangle wave
    private static final double triangleWaveBaseFunction(double t) {
        if (t < -0.25) return (-4 * t - 2); else if (t > 0.25) return (-4 * t + 2); else return (4 * t);
    }

    public TriangleWave(double frequency, double amplitude, double phase, String id) {
        super(frequency, amplitude, phase, id, TriangleWave::triangleWaveBaseFunction);
    }

    @Override
    public String toString() {
        return id + " TRIANGLE WAVE: f = " + frequency + " Hz; A = " + amplitude + "; φ = " + phase;
    }
}
