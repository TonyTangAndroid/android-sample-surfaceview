/**
 * @file SampleSurfaceViewLayer.java
 * @author Hiroo MATSUMOTO <hiroom2.mail@gmail.com>
 */
package com.hiroom2.samplesurfaceview;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Demo of SampleSurfaceViewLayer class which display and move sprite.
 */
public class SampleSurfaceViewLayer implements SampleSurfaceViewInterface {
  
  /** Weight value of SurfaceView layout. */
  public static final float SPRITE_LAYOUT_WEIGHT = 0.2f;
  public static final float SPRITE_MOVE_WEIGHT = 0.01f;
  
  /** SurfaceView layout. */
  private int mWidth;
  private int mHeight;
  
  /** Sprite stuff. */
  private final SampleSprite mSprite;
  private float mSpriteX;
  private float mSpriteY;
  private int mSpriteWidth;
  private int mSpriteHeight;
  private float mSpriteMoveX;
  
  /**
   * @param fileName
   *          Sprite name.
   * @param spriteX
   *          Sprite first x position.
   */
  public SampleSurfaceViewLayer(String fileName, int spriteX) {
    mSprite = SampleSprite.getSprite(fileName);
    mSpriteX = spriteX;
    mSpriteMoveX = 1;
  }
  
  protected boolean contains(float x, float y) {
    /** Check which x and y position is in SurfaceView layout. */
    return x >= 0 && x < 0 + mWidth && y >= 0 && y < 0 + mHeight;
  }
  
  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width,
      int height) {
    mWidth = width;
    mHeight = height;
    
    /** Set sprite stuff with SurfaceView layout and layout weight. */
    mSpriteWidth = (int) (SPRITE_LAYOUT_WEIGHT * mWidth);
    mSpriteHeight = (int) (SPRITE_LAYOUT_WEIGHT * mHeight);
    mSpriteMoveX = mSpriteMoveX < 0 ? -SPRITE_MOVE_WEIGHT * mWidth
        : SPRITE_MOVE_WEIGHT * mWidth;
    
    /** Center of vertical axis. */
    mSpriteY = (mHeight - mSpriteHeight) / 2;
  }
  
  @Override
  public void draw(Canvas canvas) {
    mSpriteX += mSpriteMoveX;
    /** If sprite is reached to edge of SurfaceView, switch moving direction. */
    if ((!contains(mSpriteX, mSpriteY) && mSpriteMoveX < 0)
        || (!contains(mSpriteX + mSpriteWidth, mSpriteY + mSpriteHeight) && mSpriteMoveX >= 0))
      mSpriteMoveX *= -1;
    mSprite.draw(canvas, mSpriteX, mSpriteY, mSpriteWidth, mSpriteHeight, 255);
  }
}
