package com.cardbookvr.visualizevr.visualizations;

import com.cardbook.renderbox.Transform;
import com.cardbook.renderbox.components.Plane;
import com.cardbookvr.visualizevr.Visualization;
import com.cardbookvr.visualizevr.VisualizerBox;
import com.cardbookvr.visualizevr.BasicEQMaterial;

/**
 * Created by Jonathan on 2/20/2016.
 */
public class BasicEQVisualization extends Visualization {
    static final String TAG = "BasicEQVisualization";

    public BasicEQVisualization(VisualizerBox visualizerBox) {
        super(visualizerBox);
    }

    @Override
    public void setup() {
        new Transform()
                //.setLocalPosition(-5, 0, 0)
                //.setLocalRotation(0,90,0)
                .setLocalPosition(0, 0, -5)
                .setLocalScale(5, 1, 1)
                .addComponent(new Plane()
                        .setMaterial(new BasicEQMaterial()
                                .setBuffers(Plane.vertexBuffer, Plane.texCoordBuffer, Plane.indexBuffer, Plane.numIndices)));
    }

    @Override
    public void transitionIn() {
    }

    @Override
    public void preDraw() {
    }

    @Override
    public void postDraw() {
    }

    @Override
    public void transitionOut() {
    }
}
