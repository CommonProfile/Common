package com.lavamusic;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Midi {
    private static Midi common;
    private static final Object LockThis = new Object();
    public synchronized static Midi get() {
        synchronized (LockThis) {
            if (null == common) {
                common = new Midi();
            }
        }
        return common;
    }

    public int modeState(){
        return 1;
    }

    public boolean back(){
        return true;
    }

    private byte[] shortToByteArray(short data) {
        return new byte[]{(byte) (data & 0xff), (byte) ((data >>> 8) & 0xff)};
    }

    public void midiWrite(DataOutputStream outFile,boolean mono,long mySampleRate,String fileToWrite){
        try {
            long mySubChunk1Size = 16;
            int myBitsPerSample = 16;
            int myFormat = 1;
            long myChannels = ((mono) ? 1 : 2);
            long myByteRate = mySampleRate * myChannels * myBitsPerSample / 8;
            int myBlockAlign = (int) (myChannels * myBitsPerSample / 8);


            outFile.writeBytes("RIFF"); // 00 - RIFF
            outFile.write(intToByteArray(0/* (int)myChunkSize */), 0, 4); // 04 - how big is the rest of this file?
            outFile.writeBytes("WAVE"); // 08 - WAVE
            outFile.writeBytes("fmt "); // 12 - fmt
            outFile.write(intToByteArray((int) mySubChunk1Size), 0, 4); // 16 - size of this chunk
            outFile.write(shortToByteArray((short) myFormat), 0, 2); // 20 - what is the audio format? 1 for PCM = Pulse Code Modulation
            outFile.write(shortToByteArray((short) myChannels), 0, 2); // 22 - mono or stereo? 1 or 2? (or 5 or ???)
            outFile.write(intToByteArray((int) mySampleRate), 0, 4); // 24 - samples per second (numbers per second)
            outFile.write(intToByteArray((int) myByteRate), 0, 4); // 28 - bytes per second
            outFile.write(shortToByteArray((short) myBlockAlign), 0, 2); // 32 - # of bytes in one sample, for all channels
            outFile.write(shortToByteArray((short) myBitsPerSample), 0, 2); // 34 - how many bits in a sample(number)? usually 16 or 24
            outFile.writeBytes("data"); // 36 - data
            outFile.write(intToByteArray(0/* (int)myChunkSize */), 0, 4); // 40 - how big is this data chunk

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private byte[] intToByteArray(int i) {
        byte[] b = new byte[4];
        b[0] = (byte) (i & 0xFF);
        b[1] = (byte) ((i >> 8) & 0xFF);
        b[2] = (byte) ((i >> 16) & 0xFF);
        b[3] = (byte) ((i >> 24) & 0xFF);
        return b;
    }

    public void midiFinish(String fileToWrite,long filesize,boolean mono,DataOutputStream outFile){
        try {
            outFile.flush();
            outFile.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        long myDataSize = filesize;
        int myBitsPerSample = 16;
        long myChannels = ((mono) ? 1 : 2);
        long myChunk2Size = myDataSize * myChannels * myBitsPerSample / 8;
        long myChunkSize = 36 + myChunk2Size;

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileToWrite, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            raf.seek(04);
            raf.write(intToByteArray((int) myChunkSize));
            raf.seek(40);
            raf.write(intToByteArray((int) myDataSize));
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getRate(){
        return new int[]{8000, 11025, 16000, 22050, 44100, 48000, 88200, 96000};
    }

    public String getSoundfontsSet(String s){
        return (s.startsWith("#") ? "#" : "") + "soundfont \"" + s + "\"\n";
    }

    public String getFontsPath(){
        return "/soundfonts/8Rock11e.sf2";
    }
    public String getCfgPath(){
        return "/config/lavamidi.cfg";
    }

    public byte[] dealDatas(byte[] mono,byte[] data){
        for (int i = 0; i < mono.length / 2; ++i) {
            int HI = 1;
            int LO = 0;
            int left = (data[i * 4 + HI] << 8) | (data[i * 4 + LO] & 0xff);
            int right = (data[i * 4 + 2 + HI] << 8) | (data[i * 4 + 2 + LO] & 0xff);
            int avg = (left + right) / 2;
            mono[i * 2 + HI] = (byte) ((avg >> 8) & 0xff);
            mono[i * 2 + LO] = (byte) (avg & 0xff);
        }
        return mono;
    }

    public String[] control(){
        String[] s = {
            "PM_REQ_MIDI", //0
                    "PM_REQ_INST_NAME", //1
                    "PM_REQ_DISCARD", //2
                    "PM_REQ_FLUSH", //3
                    "PM_REQ_GETQSIZ", //4
                    "PM_REQ_SETQSIZ", //5
                    "PM_REQ_GETFRAGSIZ", //6
                    "PM_REQ_RATE", //7
                    "PM_REQ_GETSAMPLES", //8
                    "PM_REQ_PLAY_START", //9
                    "PM_REQ_PLAY_END", //10
                    "PM_REQ_GETFILLABLE", //11
                    "PM_REQ_GETFILLED", //12
                    "PM_REQ_OUTPUT_FINISH", //13
                    "PM_REQ_DIVISIONS"//14
        };
        return s;
    }

}
