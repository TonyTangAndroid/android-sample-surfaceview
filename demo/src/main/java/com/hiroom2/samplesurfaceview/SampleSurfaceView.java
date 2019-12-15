/**
 * @file SampleSurfaceView.java
 * @author Hiroo MATSUMOTO <hiroom2.mail@gmail.com>
 */
package com.hiroom2.samplesurfaceview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * SampleSurfaceView is a sample for SurfaceView and SurfaceHolder.Callback.
 * This has multiple draw layer for overlaying multiple draw objects and use
 * draw thread which is out of SurfaceView. If you have only one SurfaceView,
 * draw thread should be in SurfaceView.
 */
public class SampleSurfaceView extends SurfaceView implements
    SurfaceHolder.Callback {
  
  /** SurfaceHolder for Surface.Callback. */
  private SurfaceHolder mSurfaceHolder;
  
  /** Draw thread. */
  private SampleThread mThread;
  
  /** Runnable registered to draw thread, which calls draw(). */
  private Runnable mRunnable;
  
  /** Layer List on SurfaceView. */
  private List<SampleSurfaceViewInterface> mLayerInterface;
  
  /**
   * With SurfaceHolder.addCallback method SurfaceHolder.Callback like
   * surfaceCreated will be called.
   */
  private void initialized() {
    mSurfaceHolder = getHolder();
    mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
    mSurfaceHolder.addCallback(this);
    mThread = null;
    mRunnable = new Runnable() {
      @Override
      public void run() {
        draw();
      }
    };
    mLayerInterface = new ArrayList<SampleSurfaceViewInterface>();
  }
  
  public SampleSurfaceView(Context context) {
    super(context);
    SampleLog.d("SampleSurfaceView(Context context)");
    initialized();
  }
  
  /**
   * A constructor of SurfaceView(Context context, AttributeSet attrs) will be
   * called from setContentView() in Activity.
   */
  public SampleSurfaceView(Context context, AttributeSet attrs) {
    super(context, attrs);
    SampleLog.d("SampleSurfaceView(Context context, AttributeSet attrs)");
    initialized();
  }
  
  public SampleSurfaceView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    SampleLog
        .d("SampleSurfaceView(Context context, AttributeSet attrs, int defStyle)");
    initialized();
  }
  
  /** This method must be called before surfaceChanged */
  public void setThread(SampleThread thread) {
    mThread = thread;
  }
  
  public void addLayerInterface(SampleSurfaceViewInterface layerInterface) {
    synchronized (mLayerInterface) {
      mLayerInterface.add(layerInterface);
    }
  }
  
  public void removeLayer(SampleSurfaceViewInterface layerInterface) {
    synchronized (mLayerInterface) {
      mLayerInterface.remove(layerInterface);
    }
  }
  
  /** A method of surfaceCreated will be called after surface created. */
  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    SampleLog.d("surfaceCreated(SurfaceHolder holder");
    /** Do nothing. */
  }
  
  /**
   * A method of surfaceChanged will be called after surfaceCreated called or
   * orientation of SurfaceView changed.
   */
  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
      int height) {
    SampleLog
        .d("surfaceChanged(SurfaceHolder holder, int format, int width, int height)");
    
    /** mThread must be set before this. */
    assert (mThread != null);
    
    /** Resize multiple layers on SurfaceView. */
    synchronized (mLayerInterface) {
      for (SampleSurfaceViewInterface layerInterface : mLayerInterface)
        layerInterface.surfaceChanged(holder, format, width, height);
    }
    
    /** Enable mRunnable which calls draw() */
    mThread.addRunnable(mRunnable);
  }
  
  /**
   * A method of surfaceChanged will be called after surfaceCreated called
   * orientation of SurfaceView changed before surfaceChanged.
   */
  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    SampleLog.d("surfaceDestroyed(SurfaceHolder holder)");
    mThread.removeRunnable(mRunnable);
  }
  
  /**
   * A draw method should be called from draw thread. I think that one draw
   * thread should be responsible for drawing multiple SurfaceView. So, draw
   * thread should be implemented out of SurfaceView. If override draw(Canvas
   * canvas), layout editor on eclipse will be error.
   */
  public void draw() {
    Canvas canvas = mSurfaceHolder.lockCanvas();
    if (canvas == null)
      return;
    
    /** Clear canvas. */
    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    
    /** Draw multiple layers on SurfaceView. */
    synchronized (mLayerInterface) {
      for (SampleSurfaceViewInterface layerInterface : mLayerInterface)
        layerInterface.draw(canvas);
    }
    
    mSurfaceHolder.unlockCanvasAndPost(canvas);
  }
  
  /**
   * Each SurfaceView has its onTouchEvent. event.getX() and event.getY() are
   * positions on SurfaceView not display.
   */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    SampleLog.d("event.getX() = " + event.getX() + ", event.getY() = "
        + event.getY());
    return true;
  }
  
}
