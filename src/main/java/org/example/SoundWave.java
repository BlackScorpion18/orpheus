package org.example;

public abstract class SoundWave implements SoundWaveInterface {
    // in Java, % gives the remainder of a division, not the true modulo; this function is for that exactly
    public static double trueModulo(double a, double b) {
        return ((a % b) + b) % b;
    }

    // value for floating point tolerance (to avoid crashing when faced with small, irrelevant errors)
    protected static final double floatTolerance = 1e-12;

    double frequency;   // the frequency of the wave. measured in hertz, Hz
    double amplitude;   // the amplitude of the wave. measured in decibels, dB
    double phase;       // the phase of the wave. measured in seconds, s
    String id;

    public SoundWave(double frequency, double amplitude, double phase, String id) {
        if (!(frequency > 0)) throw new IllegalArgumentException("SoundWave frequency must be greater than 0");
        if (!(amplitude > 0)) throw new IllegalArgumentException("SoundWave amplitude must be greater than 0");
        if (id == null || id.isEmpty()) throw new IllegalArgumentException("SoundWave id cannot be null or empty");
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.phase = phase;
        this.id = id;
    }

    // getters
    public double getFrequency() {
        return this.frequency;
    }

    public double getAmplitude() {
        return this.amplitude;
    }

    public double getPhase() {
        return this.phase;
    }

    public String getId() {
        return this.id;
    }

    public double getPeriod() {
        return 1/this.frequency;
    }

    // setters
    public void setFrequency(double frequency) {
        if (frequency <= 0) throw new IllegalArgumentException("Illegal argument: the frequency must be greater than 0");
        this.frequency = frequency;
    }

    public void setAmplitude(double amplitude) {
        if (amplitude <= 0) throw new IllegalArgumentException("Illegal argument: the amplitude must be greater than 0");
        this.amplitude = amplitude;
    }

    public void setPhase(double phase) {
        this.phase = phase;
    }

    public void setId(String id) {
        if (id == null || id.isEmpty()) throw new IllegalArgumentException("SoundWave id cannot be null or empty");
        this.id = id;
    }

    public void setPeriod(double period) {
        if (period <= 0) throw new IllegalArgumentException("Illegal argument: the period must be greater than 0");
        this.frequency = 1 / period;
    }

    @Override
    public String toString() {
        return id + " WAVE: f = " + frequency + " Hz; A = " + amplitude + "; φ = " + phase;
    }
}
