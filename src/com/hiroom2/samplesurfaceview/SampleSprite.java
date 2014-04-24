/**
 * @file SampleSprite.java
 * @author Hiroo MATSUMOTO <hiroom2.mail@gmail.com>
 */
package com.hiroom2.samplesurfaceview;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * SampleSprite class for each Bitmap. But bitmap should be one big one which
 * is called sprite sheet. But I don't create it this time.
 */
public class SampleSprite {
  
  private static Resources mResources;
  private static String mPackageName;
  private static Map<String, SampleSprite> mSprite;
  private static RectF mDstRect;
  
  public static void initialized(Context context) {
    mResources = context.getResources();
    mPackageName = context.getPackageName();
    mSprite = new HashMap<String, SampleSprite>();
    mDstRect = new RectF();
  }
  
  /**
   * Singleton method for creating sprite.
   * 
   * @param fileName
   *          Sprite file name
   * @return sprite
   */
  public static SampleSprite getSprite(String fileName) {
    SampleSprite sprite = mSprite.get(fileName);
    if (sprite == null) {
      sprite = new SampleSprite(fileName);
      mSprite.put(fileName, sprite);
    }
    return sprite;
  }
  
  private final Bitmap mBitmap;
  private final Rect mSrcRect;
  private final Paint mPaint;
  
  private SampleSprite(String fileName) {
    int id = mResources.getIdentifier(fileName, "drawable", mPackageName);
    mBitmap = BitmapFactory.decodeResource(mResources, id);
    mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
    mPaint = new Paint();
  }
  
  public void draw(Canvas canvas, float x, float y, float w, float h, int a) {
    mDstRect.set(x, y, x + w, y + h);
    mPaint.setAlpha(a);
    canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, mPaint);
  }
  
}
