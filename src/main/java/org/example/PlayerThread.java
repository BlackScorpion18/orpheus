package org.example;

import java.util.ArrayList;

public class PlayerThread extends Thread {
    ArrayList<SoundWave> waveArray;

    public PlayerThread(ArrayList<SoundWave> waveArrayList) {
        this.waveArray = waveArrayList;
    }

    public PlayerThread() {

    }

    public void setWaveArray(ArrayList<SoundWave> waveArrayList) {
        this.waveArray = waveArrayList;
    }

    public ArrayList<SoundWave> getWaveArray() {
        return waveArray;
    }
}
