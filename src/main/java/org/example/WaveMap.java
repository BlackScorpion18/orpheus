package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class WaveMap {
    public static ArrayList<WaveMap> allWaveMapsArray = new ArrayList<>();
    private String id;
    private HashMap<String, SoundWave> soundwaveMap;

    /**
     returns the wavemap with the specified ID, or null if no such wavemap exists
     */
    public static WaveMap getWaveMapWithId(String wavemapId) {
        for (WaveMap wmap : allWaveMapsArray) {
            if (wmap.getId().equals(wavemapId)) return wmap;
        }
        return null;
    }

    /** returns true if the wavemap exists, false otherwise */
    public static boolean waveMapExists(String wavemapId) {
        return (getWaveMapWithId(wavemapId) != null);
    }

    public WaveMap(String id) {
        this.id = id;
        this.soundwaveMap = new HashMap<>();

        allWaveMapsArray.add(this);
    }

    public WaveMap(String id, HashMap<String, SoundWave> soundwaveMap) {
        this.id = id;
        this.soundwaveMap = soundwaveMap;

        allWaveMapsArray.add(this);
    }

    public String getId() {
        return id;
    }

    public HashMap<String, SoundWave> getSoundwaveMap() {
        return soundwaveMap;
    }

    // returns true if the id renaming was successful, false otherwise
    public boolean setId(String id) {
        if (id.equals("main")) return false;
        for (WaveMap wmap : allWaveMapsArray) {
            if (id.equals(wmap.getId())) return false;
        }

        this.id = id;
        return true;
    }

    public void setSoundwaveMap(HashMap<String, SoundWave> soundwaveMap) {
        this.soundwaveMap = soundwaveMap;
    }

    /** empties the wavemap */
    public void clearSoundwaveMap() {
        this.setSoundwaveMap(new HashMap<>());
    }

    /** returns true if the wavemap contains a wave with the specified ID, false otherwise */
    public boolean contains(String waveId) {
        return soundwaveMap.containsKey(waveId);
    }

    /** returns a soundwave with the specified ID if it exists in the wavemap, null otherwise */
    public SoundWave getWave(String waveId) {
        return soundwaveMap.get(waveId);
    }

    /** adds a soundwave to this wavemap.
    if a soundwave with the same ID is present, it will be replaced and returned;
     otherwise, null is returned
    */
    private SoundWave addSoundWave(SoundWave wave) {
        return soundwaveMap.put(wave.getId(), wave);
    }

    /** removes a soundwave from this wavemap.
    returns the removed soundwave, or null if nothing is removed
    */
    public SoundWave removeSoundWave(String waveId) {
        return soundwaveMap.remove(waveId);
    }

    /** moves a soundwave from a wave map to another.
     returns true if a move happens, false if it doesn't.
    reminder that waves are, by default, created in the main wavemap.
    */
    public boolean moveSoundWaveHere(String waveId, String wavemapId) {
        WaveMap wavemap = getWaveMapWithId(wavemapId);
        if (wavemap == null) return false;

        SoundWave waveToMove = wavemap.removeSoundWave(waveId);
        if (waveToMove != null) {
            this.addSoundWave(waveToMove);
            return true;
        } else return false;
    }

    @Override
    public String toString() {
        String allWavesString = "";
        for (SoundWave wave : soundwaveMap.values()) {
            allWavesString += wave.toString() + "\n";
        }
        return "Wavemap with ID " + id + "; contains " + soundwaveMap.size() + " waves:\n" + allWavesString;
    }
}
