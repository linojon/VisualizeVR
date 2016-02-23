package com.cardbookvr.visualizevr.visualizations;

import com.cardbook.renderbox.Transform;
import com.cardbook.renderbox.components.Cube;
import com.cardbookvr.visualizevr.Visualization;
import com.cardbookvr.visualizevr.VisualizerBox;

/**
 * Created by Jonathan on 2/9/2016.
 */
public class GeometricVisualization extends Visualization {
    static final String TAG = "GeometricVisualization";

    Transform[] cubes;
    Cube[] cubeRenderers;

    public GeometricVisualization(VisualizerBox visualizerBox) {
        super(visualizerBox);
    }

    @Override
    public void setup() {
        cubeRenderers = new Cube[VisualizerBox.captureSize / 2];
        cubes = new Transform[VisualizerBox.captureSize / 2];
        float offset = -3f;
        float scaleFactor = (offset * -2) / cubes.length;
        for(int i = 0; i < cubes.length; i++) {
            cubeRenderers[i] = new Cube(true);
            cubes[i] = new Transform()
                    .setLocalPosition(offset, -2, -5)
                    .addComponent(cubeRenderers[i]);
            offset += scaleFactor;
        }
    }

    @Override
    public void preDraw() {
        if (VisualizerBox.audioBytes != null) {
            float scaleFactor = 3f / cubes.length;
            for(int i = 0; i < cubes.length; i++) {
                cubes[i].setLocalScale(scaleFactor, VisualizerBox.audioBytes[i] * 0.01f, 1);
            }
        }
    }

    @Override
    public void postDraw() {

    }

    @Override
    public void activate(boolean enabled) {
        active = enabled;
        for(int i = 0; i < cubes.length; i++) {
            cubeRenderers[i].enabled = enabled;
        }
    }
}
