/**
 * @file SampleSurfaceViewLayerInterface.java
 * @author Hiroo MATSUMOTO <hiroom2.mail@gmail.com>
 */
package com.hiroom2.samplesurfaceview;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Interface between SampleSurfaceView and SampleSurfaceViewLayer.
 */
public interface SampleSurfaceViewInterface {
  
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
      int height);
  
  public void draw(Canvas canvas);
  
}
