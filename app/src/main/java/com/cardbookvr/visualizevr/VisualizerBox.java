package com.cardbookvr.visualizevr;

import android.media.audiofx.Visualizer;
import android.util.Log;

import com.google.vrtoolkit.cardboard.CardboardView;

/**
 * Created by Jonathan on 2/6/2016.
 */
public class VisualizerBox {
    static final String TAG = "VisualizerBox";
    final float MIN_THRESHOLD = 1.5f;

    Visualization activeViz;
    Visualizer visualizer;

    public static int captureSize;

    public static byte[] audioBytes;
    public static byte[] fftBytes, fftNorm;
    public static float[] fftPrep;

    public VisualizerBox(final CardboardView cardboardView){
        visualizer = new Visualizer(0);
        captureSize = Visualizer.getCaptureSizeRange()[0];
        Log.d(TAG, "Capture size: " + captureSize);
        //Capture size will define our fftBytes size
        visualizer.setCaptureSize(captureSize);

//        fftPrep = new float[captureSize / 2];
//        fftNorm = new byte[captureSize / 2];

        Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                audioBytes = bytes;
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
//                fftBytes = bytes;
//                float max = 0;
//                for(int i = 0; i < fftPrep.length; i++) {
//                    if(fftBytes.length > i * 2) {
//                        fftPrep[i] = (float)Math.sqrt(fftBytes[i * 2] * fftBytes[i * 2] + fftBytes[i * 2 + 1] * fftBytes[i * 2 + 1]);
//                        if(fftPrep[i] > max){
//                            max = fftPrep[i];
//                        }
//                    }
//                }
//                float coeff = 1 / max;
//                for(int i = 0; i < fftPrep.length; i++) {
//                    if(fftPrep[i] < MIN_THRESHOLD){
//                        fftPrep[i] = 0;
//                    }
//                    fftNorm[i] = (byte)(fftPrep[i] * coeff * 255);
//                }
            }
        };

        visualizer.setDataCaptureListener(captureListener, Visualizer.getMaxCaptureRate(), true, true);
        //Enable Visualizer (always listening)
        visualizer.setEnabled(true);
    }

    public void setup() {
        if(activeViz != null)
            activeViz.setup();
    }

    public void preDraw() {
        if(activeViz != null)
            activeViz.preDraw();
    }

    public void postDraw() {
        if(activeViz != null)
            activeViz.postDraw();
    }
}
