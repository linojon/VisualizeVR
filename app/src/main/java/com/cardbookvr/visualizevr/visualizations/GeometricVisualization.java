package com.cardbookvr.visualizevr.visualizations;

import com.cardbook.renderbox.Transform;
import com.cardbook.renderbox.components.Cube;
import com.cardbook.renderbox.components.Plane;
import com.cardbookvr.visualizevr.Visualization;
import com.cardbookvr.visualizevr.VisualizerBox;
import com.cardbookvr.visualizevr.WaveformMaterial;

/**
 * Created by Jonathan on 2/9/2016.
 */
public class GeometricVisualization extends Visualization {
    static final String TAG = "GeometricVisualization";

    Transform[] cubes;

    public GeometricVisualization(VisualizerBox visualizerBox) {
        super(visualizerBox);
    }

    @Override
    public void setup() {
        cubes = new Transform[VisualizerBox.captureSize / 2];
        float offset = -3f;
        float scaleFactor = (offset * -2) / cubes.length;
        for(int i = 0; i < cubes.length; i++) {
            cubes[i] = new Transform()
                    .setLocalPosition(offset, -2, -5)
                    .addComponent(new Cube(true));
            offset += scaleFactor;
        }

        new Transform()
                .setLocalPosition(-5,0,0)
                .setLocalRotation(0,90,0)
                .setLocalScale(5, 1, 1)
                .addComponent(new Plane()
                        .setMaterial(new WaveformMaterial()
                                .setBuffers(Plane.vertexBuffer, Plane.texCoordBuffer, Plane.indexBuffer, Plane.numIndices)));

    }

    @Override
    public void transitionIn() {

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
    public void transitionOut() {

    }
}
