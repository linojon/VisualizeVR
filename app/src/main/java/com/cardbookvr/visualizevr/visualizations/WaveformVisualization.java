package com.cardbookvr.visualizevr.visualizations;

import com.cardbook.renderbox.Transform;
import com.cardbook.renderbox.components.Plane;
import com.cardbook.renderbox.components.RenderObject;
import com.cardbookvr.visualizevr.Visualization;
import com.cardbookvr.visualizevr.VisualizerBox;
import com.cardbookvr.visualizevr.WaveformMaterial;

/**
 * Created by Jonathan on 2/20/2016.
 */
public class WaveformVisualization extends Visualization {
    static final String TAG = "WaveformVisualization";

    RenderObject plane;

    public WaveformVisualization(VisualizerBox visualizerBox) {
        super(visualizerBox);
    }

    @Override
    public void setup() {
        plane = new Plane().setMaterial(new WaveformMaterial()
                .setBuffers(Plane.vertexBuffer, Plane.texCoordBuffer, Plane.indexBuffer, Plane.numIndices));
        new Transform()
                .setLocalPosition(-5, 0, 0)
                .setLocalRotation(0, 90, 0)
//                .setLocalPosition(0, 0, -5)
//                .setLocalScale(5, 1, 1)
                .addComponent(plane);
    }

    @Override
    public void preDraw() {
    }

    @Override
    public void postDraw() {
    }

    @Override
    public void activate(boolean enabled) {
        active = enabled;
        plane.enabled = enabled;
    }
}
