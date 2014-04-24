/**
 * @file SampleLog.java
 * @author Hiroo MATSUMOTO <hiroom2.mail@gmail.com>
 */
package com.hiroom2.samplesurfaceview;

import android.util.Log;

public class SampleLog {
  
  private static boolean mDebug;
  private static boolean mInfo;
  private static boolean mError;
  private static String mFilter;
  
  static {
    mDebug = true;
    mInfo = true;
    mError = true;
    mFilter = null;
  }
  
  private static boolean isFiltered(String tag, String str) {
    if (mFilter == null)
      return true;
    if (tag.indexOf(mFilter) != -1)
      return true;
    return str.indexOf(mFilter) != -1;
  }
  
  public static void setDebug(boolean enable) {
    mDebug = enable;
  }
  
  public static void setInfo(boolean enable) {
    mInfo = enable;
  }
  
  public static void setError(boolean enable) {
    mError = enable;
  }
  
  public static void setFilter(String filter) {
    mFilter = filter;
  }
  
  private static String getTag() {
    Throwable throwable = new Throwable();
    StackTraceElement[] stack = throwable.getStackTrace();
    return stack[2].getFileName() + "[" + stack[2].getLineNumber() + "]";
  }
  
  public static void d(String tag, String str) {
    if (mDebug && isFiltered(tag, str))
      Log.d(tag, str);
  }
  
  public static void d(String str) {
    d(getTag(), str);
  }
  
  public static void i(String tag, String str) {
    if (mInfo && isFiltered(tag, str))
      Log.i(tag, str);
  }
  
  public static void i(String str) {
    i(getTag(), str);
  }
  
  public static void e(String tag, String str) {
    if (mError && isFiltered(tag, str))
      Log.e(tag, str);
  }
  
  public static void e(String str) {
    e(getTag(), str);
  }
  
}
