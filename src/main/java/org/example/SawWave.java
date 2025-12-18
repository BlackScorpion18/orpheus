package org.example;

public class SawWave extends CustomPeriodicWave {
    private static final double sawWaveBaseFunction(double t) {
        return 2 * t;
    }

    public SawWave(double frequency, double amplitude, double phase, String id) {
        super(frequency, amplitude, phase, id, SawWave::sawWaveBaseFunction);
    }

    @Override
    public String toString() {
        return id + " SAW WAVE: f = " + frequency + " Hz; A = " + amplitude + "; φ = " + phase;
    }
}
