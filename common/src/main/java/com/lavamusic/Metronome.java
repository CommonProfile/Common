package com.lavamusic;

import android.content.Context;

import java.io.InputStream;
import java.lang.reflect.Field;

public class Metronome {
    private static Metronome common;
    private static final Object LockThis = new Object();

    public synchronized static Metronome get() {
        synchronized (LockThis) {
            if (null == common) {
                common = new Metronome();
            }
        }
        return common;
    }

    public int val() {
        return 88200;
    }

    public byte[] getSig(int periodSize, Field[] fields, int clicChoice, Context context) {

        int clicSize = 0;

        //每个16位样本使用两个字节来表示
        byte[] sig = new byte[2 * periodSize];

        try {

            int id = fields[clicChoice - 2].getInt(null);
            //Raw资源加载
            InputStream is = context.getResources().openRawResource(id);
            clicSize = is.read(sig, 0, periodSize);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //添加空白音
        for (int i = clicSize; i < 2 * periodSize; i++)
            sig[i] = 0;
        return sig;
    }

    public double[] getSig(boolean noteIs8AndThreeSound, int sampleRate, double bpm, int note, double freq, int clicSize) {
        int periodSize;
        if (noteIs8AndThreeSound) {
            periodSize = (int) ((60. * sampleRate) / bpm);
        } else {
            periodSize = (int) ((60. * sampleRate) / bpm / note);
        }

        double wStep = (2 * Math.PI * freq) / sampleRate;//节奏
        double[] sig = new double[periodSize];

        //将clicSize (ms)转换为sample，如果bpm过高，则将其裁剪为半个周期
        clicSize = (clicSize * sampleRate) / 1000;
        if (clicSize >= periodSize)
            clicSize = periodSize / 2;

//        LogUtil.i("vijoz-periodSize:" + periodSize + "--wStep:" + wStep + "--sig.size:" + sig.length + "--clicSize:" + clicSize);

        //Tic音
        for (int i = 0; i < clicSize; i++) {
            sig[i] = Math.sin(i * wStep);
//            LogUtil.i("vijoz---" + sig[i]);
        }

        //添加空白音
        for (int i = clicSize; i < periodSize; i++)
            sig[i] = 0;

        return sig;
    }
}
