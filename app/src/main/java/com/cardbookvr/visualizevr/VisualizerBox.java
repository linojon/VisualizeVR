package com.cardbookvr.visualizevr;

import android.media.audiofx.Visualizer;
import android.opengl.GLES20;
import android.util.Log;

import com.cardbook.renderbox.RenderBox;
import com.google.vrtoolkit.cardboard.CardboardView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Jonathan on 2/6/2016.
 */
public class VisualizerBox {
    static final String TAG = "VisualizerBox";
    final float MIN_THRESHOLD = 1.5f;

    Visualization activeViz;
    Visualizer visualizer;

    public static int captureSize;

    public static int audioTexture = -1;
    public static int fftTexture = -1;

    public static byte[] audioBytes;
    public static byte[] fftBytes, fftNorm;
    public static float[] fftPrep;

    public VisualizerBox(final CardboardView cardboardView){
        visualizer = new Visualizer(0);
        captureSize = Visualizer.getCaptureSizeRange()[0];
        Log.d(TAG, "Capture size: " + captureSize);
        //Capture size will define our fftBytes size
        visualizer.setCaptureSize(captureSize);

        fftPrep = new float[captureSize / 2];
        fftNorm = new byte[captureSize / 2];

        Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate){
                audioBytes = bytes;
                loadTexture(cardboardView, audioTexture, bytes);
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {
                fftBytes = bytes;
                float max = 0;
                for(int i = 0; i < fftPrep.length; i++) {
                    if(fftBytes.length > i * 2) {
                        fftPrep[i] = (float)Math.sqrt(fftBytes[i * 2] * fftBytes[i * 2] + fftBytes[i * 2 + 1] * fftBytes[i * 2 + 1]);
                        if(fftPrep[i] > max){
                            max = fftPrep[i];
                        }
                    }
                }
                float coeff = 1 / max;
                for(int i = 0; i < fftPrep.length; i++) {
                    if(fftPrep[i] < MIN_THRESHOLD){
                        fftPrep[i] = 0;
                    }
                    fftNorm[i] = (byte)(fftPrep[i] * coeff * 255);
                }
                loadTexture(cardboardView, fftTexture, fftNorm);
            }
        };

        visualizer.setDataCaptureListener(captureListener, Visualizer.getMaxCaptureRate(), true, true);
        //Enable Visualizer (always listening)
        visualizer.setEnabled(true);
    }

    public void setup() {
        audioTexture = genTexture();
        fftTexture = genTexture();
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

    public static int genTexture(){
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);
        RenderBox.checkGLError("VisualizerBox GenTexture");
        if (textureHandle[0] != 0) {
            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        }
        if (textureHandle[0] == 0){
            throw new RuntimeException("Error loading texture.");
        }
        return textureHandle[0];
    }

    //From http://stackoverflow.com/questions/14290096/how-to-create-a-opengl-texture-from-byte-array-in-android
    public static void loadTexture(CardboardView cardboardView, final int textureId, byte[] bytes){
        if(textureId < 0)
            return;
        final ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length * 4);
        final int length = bytes.length;
        buffer.order(ByteOrder.nativeOrder());
        buffer.put(bytes);
        buffer.position(0);
        cardboardView.queueEvent(new Runnable() {
            @Override
            public void run() {
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
                GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, length, 1, 0,
                        GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, buffer);
            }
        });
    }
}
