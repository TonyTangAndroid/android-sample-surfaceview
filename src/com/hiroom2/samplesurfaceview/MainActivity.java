/**
 * @file MainActivity.java
 * @author Hiroo MATSUMOTO <hiroom2.mail@gmail.com>
 */
package com.hiroom2.samplesurfaceview;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
  
  /** Frame per second for draw thread. */
  public static final int DRAW_THREAD_FPS = 32;
  
  /** First x position for foreground sprite. */
  public static final int FIRST_X_POSITION = 300;
  
  /** Draw thread multiple SurfaceView on this Activity. */
  private SampleThread mThread;
  
  public void setSurfaceView(int id, String fileName1, String fileName2) {
    SampleSurfaceView surfaceView = (SampleSurfaceView) findViewById(id);
    
    /** Take thread to SurfaceView which can register draw Runnable later. */
    surfaceView.setThread(mThread);
    
    /** Set draw layer on SurfaceView. Layer layout is not yet set. */
    surfaceView.addLayerInterface(new SampleSurfaceViewLayer(fileName1, 0));
    surfaceView.addLayerInterface(new SampleSurfaceViewLayer(fileName2,
        FIRST_X_POSITION));
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SampleLog.d("onCreate");
    
    /** Set Context. */
    SampleLog.setDebug(true);
    SampleSprite.initialized(this);
    
    setContentView(R.layout.activity_main);
    
    /** Create wrapper thread which can call start() and stop() repeatedly. */
    mThread = new SampleThread(DRAW_THREAD_FPS);
    setSurfaceView(R.id.sampleSurfaceView1, "yellow_droid", "purple_droid");
    setSurfaceView(R.id.sampleSurfaceView2, "purple_droid", "yellow_droid");
  }
  
  @Override
  public void onResume() {
    super.onResume();
    SampleLog.d("onResume");
    
    /** Start thread loop. */
    mThread.start();
  }
  
  @Override
  public void onPause() {
    super.onPause();
    SampleLog.d("onPause");
    
    /** Stop thread loop. */
    mThread.stop();
  }
  
}
