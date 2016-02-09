package com.cardbookvr.visualizevr;

/**
 * Created by Jonathan on 2/6/2016.
 */
public abstract class Visualization {
    VisualizerBox visualizerBox;            //owner
    public Visualization(VisualizerBox visualizerBox){
        this.visualizerBox = visualizerBox;
    }
    public abstract void setup();
    public abstract void transitionIn();
    public abstract void preDraw();
    public abstract void postDraw();
    public abstract void transitionOut();
}
