package org.example;

public class SineWave extends SoundWave {
    public SineWave(double frequency, double amplitude, double phase, String id) {
        super(frequency, amplitude, phase, id);
    }

    // returns the current value of the function
    public double returnCurrentValue(double time) {
        return amplitude * Math.sin(2 * Math.PI * frequency * time + phase);
    }

    @Override
    public String toString() {
        return id + " SINE WAVE: f = " + frequency + " Hz; A = " + amplitude + "; φ = " + phase;
    }
}
