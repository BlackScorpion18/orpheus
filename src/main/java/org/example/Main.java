package org.example;

import javax.sound.sampled.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void printWaveHashmapValues(HashMap<String, SoundWave> hashMap) {
        for (SoundWave wave : hashMap.values()) {
            System.out.println(wave);
        }
    }

    public static void main(String[] args) throws LineUnavailableException {
        float sampleRate = 44100;   // Hz
        int durationSeconds = 1;
        double frequency = 440.0;   // hz

        // Audio format: 44.1kHz, 16-bit, mono, signed, little-endian
        AudioFormat format = new AudioFormat(
                sampleRate,
                16,
                1,
                true,
                false
        );

        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();

        byte[] buffer = new byte[2]; // 16-bit = 2 bytes per sample

        HashMap<String, SoundWave> soundWaveHashMap = new HashMap<>();

        // scanner for reading commands
        Scanner commandScanner = new Scanner(System.in);

        // separate thread to handle sound playing
        Thread soundPlayerThread = new PlayerThread();

        System.out.println("welcome to Orpheus!\n");
        while (true) {
            // command parsing
            String userInput = commandScanner.nextLine();
            String[] userInputSplit = userInput.split(" ");
            switch (userInputSplit[0]) {
                case "version":
                case "-v":
                    System.out.println("----------------------");
                    System.out.println("   Orpheus v.0.0.1 ");
                    System.out.println(" CLI-based pseudo DAW ");
                    System.out.println("----------------------");
                    break;

                case "help":
                case "-h":
                    System.out.println("\nOrpheus Command Help\n");
                    System.out.println("help, -h : display this menu");
                    System.out.println("man, -m [COMMAND_NAME] : display info about a command");
                    System.out.println("version, -v : display the version of Orpheus being used");
                    System.out.println("wave, -w : create a new sound wave");
                    break;

                // man command: provides information about a specific command
                case "man":
                case "-m":
                    if (userInputSplit.length < 2) {
                        System.out.println("man, -m");
                        System.out.println(" - USAGE: man [COMMAND_NAME]");
                        System.out.println(" - EXAMPLE: man wave");
                        System.out.println(" - Displays a description and usage guide for a specified command.");
                        break;
                    }
                    switch (userInputSplit[1]) {
                        case "help":
                        case "-h":
                            System.out.println("help, -h");
                            System.out.println(" - USAGE: help");
                            System.out.println(" - Displays a list of all possible commands.");
                            break;
                        case "man":
                        case "m":
                        case "":
                        case " ":
                            System.out.println("man, -m");
                            System.out.println(" - USAGE: man [COMMAND_NAME]");
                            System.out.println(" - EXAMPLE: man wave");
                            System.out.println(" - Displays a description and usage guide for a specified command.");
                            break;
                        case "wave":
                        case "-w":
                            System.out.println("wave, -w");
                            System.out.println(" - USAGE: wave [ID] [WAVE_TYPE] [FREQUENCY] [AMPLITUDE] [PHASE]");
                            System.out.println(" - EXAMPLE: wave newSineWave sin 440 0.8 0");
                            System.out.println(" - Creates a new wave, and adds it to the wave map. If a wave with the " +
                                                  "specified ID already exists, it will be replaced by the new one.");
                            System.out.println(" - Waves have 4 base types:");
                            System.out.println("   -> sin: sine waves;");
                            System.out.println("   -> sqr: square waves;");
                            System.out.println("   -> saw: saw waves;");
                            System.out.println("   -> tri: triangle waves.");
                            break;
                        case "list":
                        case "-l":
                            System.out.println("list, -l");
                            System.out.println(" - USAGE: list");
                            System.out.println(" - Prints the existing waves.");
                            break;
                        case "remove":
                        case "-r":
                            System.out.println("remove, -r");
                            System.out.println(" - USAGE: remove [WAVE_ID]");
                            System.out.println(" - If a wave with the specified ID exists in the wave map, it will be removed.");
                        default:
                            System.out.println("Error: command " + userInputSplit[1] + " does not exist");
                            break;
                    }
                    break;

                // wave command: creates a new wave
                case "wave":
                case "-w":
                    if (userInputSplit.length != 6) {
                        System.out.println("wave requires 6 arguments, found " + userInputSplit.length);
                        break;
                    }

                    double newFrequency;
                    double newAmplitude;
                    double newPhase;
                    try {
                        newFrequency = Double.parseDouble(userInputSplit[3]);
                        newAmplitude = Double.parseDouble(userInputSplit[4]);
                        newPhase = Double.parseDouble(userInputSplit[5]);
                    } catch (Exception e) {
                        System.out.println("Error: could not parse arguments properly: are you sure you typed numbers?");
                        break;
                    }
                    String newId = userInputSplit[1];

                    SoundWave newSoundWave = null;
                    String newWaveType = userInputSplit[2];
                    switch (newWaveType) {
                        case "sin":
                            newSoundWave = new SineWave(newFrequency, newAmplitude, newPhase, newId);
                            break;
                        case "sqr":
                            newSoundWave = new SquareWave(newFrequency, newAmplitude, newPhase, newId);
                            break;
                        case "saw":
                            newSoundWave = new SawWave(newFrequency, newAmplitude, newPhase, newId);
                            break;
                        case "tri":
                            newSoundWave = new TriangleWave(newFrequency, newAmplitude, newPhase, newId);
                            break;
                        default:
                            System.out.println("Error: invalid wave type");
                            break;
                    }
                    if (!(newSoundWave == null)) {
                        System.out.println("Created new wave:");
                        System.out.println(newSoundWave);
                        soundWaveHashMap.put(newId, newSoundWave);
                    }
                    break;

                // remove command: removes waves from the wave map
                case "remove":
                case "-r":
                    if (userInputSplit.length != 2) {
                        System.out.println("remove requires 2 arguments, found " + userInputSplit.length);
                        break;
                    }

                    String idToRemove = userInputSplit[1];
                    SoundWave removedWave = soundWaveHashMap.remove(idToRemove);
                    if (removedWave == null) {
                        System.out.println("Error: wave with the ID " + idToRemove + " could not be found. No removal took place.");
                    } else System.out.println("Removed wave with ID " + idToRemove + ": \n" + removedWave);
                    break;

                case "list":
                case "-l":
                    System.out.println("ALL " + soundWaveHashMap.size() + " WAVES:");
                    printWaveHashmapValues(soundWaveHashMap);
                    break;

                default:
                    System.out.println("Unknown command. Please try again.");
                    break;
            }
        }


        /*
        // creating waves
        SineWave sineWaveC4 = new SineWave(261.33, 0.1, 0);
        SineWave sineWaveE4 = new SineWave(329.63, 0.1, 0);
        SineWave sineWaveG4 = new SineWave(392, 0.1, 0);

        for (int i = 0; i < sampleRate * durationSeconds; i++) {
            double time = i / sampleRate;
            double waveResult1 = sineWaveC4.returnCurrentValue(time);
            double waveResult2 = sineWaveE4.returnCurrentValue(time);
            double waveResult3 = sineWaveG4.returnCurrentValue(time);

            short waveSample1 = (short) (waveResult1 * Short.MAX_VALUE);
            short waveSample2 = (short) (waveResult2 * Short.MAX_VALUE);
            short waveSample3 = (short) (waveResult3 * Short.MAX_VALUE);

            int sample = waveSample1 + waveSample2 + waveSample3;

            // little-endian
            buffer[0] = (byte) (sample & 0xff);
            buffer[1] = (byte) ((sample >> 8) & 0xff);

            line.write(buffer, 0, 2);
        }

        line.drain();
        line.close();
        */
    }
}