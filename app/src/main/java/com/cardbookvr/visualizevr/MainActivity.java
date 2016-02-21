package com.cardbookvr.visualizevr;

import android.os.Bundle;

import com.cardbook.renderbox.IRenderBox;
import com.cardbook.renderbox.RenderBox;
import com.cardbook.renderbox.Transform;
import com.cardbook.renderbox.components.Cube;
import com.cardbookvr.visualizevr.visualizations.BasicEQVisualization;
import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;

public class MainActivity extends CardboardActivity implements IRenderBox {
    private static final String TAG = "MainActivity";

    CardboardView cardboardView;
    VisualizerBox visualizerBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardboardView = (CardboardView) findViewById(R.id.cardboard_view);
        cardboardView.setRestoreGLStateEnabled(false);
        cardboardView.setRenderer(new RenderBox(this, this));
        setCardboardView(cardboardView);

        visualizerBox = new VisualizerBox(cardboardView);
        visualizerBox.activeViz = new BasicEQVisualization(visualizerBox);
    }

    @Override
    public void setup() {
        new Transform()
                .setLocalPosition(0,0,-7)
                .setLocalRotation(45,60,0)
                .addComponent(new Cube(true));
        visualizerBox.setup();
    }

    @Override
    public void preDraw() {
        visualizerBox.preDraw();
    }

    @Override
    public void postDraw() {
        visualizerBox.postDraw();
    }

}
