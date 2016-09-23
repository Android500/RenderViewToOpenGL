package com.render.demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.ref.SoftReference;

/**
 * Created by huangxin on 2016/9/23.
 */
public class RendedWidget  extends CustomView{


    public static final String TAG = "LauncherAppWidgetHostView";

    private static Bitmap g = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

    public static interface UpdateListener {
        public void onWidgetUpdate(Bitmap bitmap);
    }

    private LayoutInflater mInflater;

    private Surface mSurface;

    private SurfaceTexture mSurfaceTexture;

    SoftReference<Bitmap> softReference;

    public Object O = new Object();

    Canvas mCanvas;

    private UpdateListener mUpdateListener;

    public void setUpdateListener(UpdateListener mUpdateListener) {
        this.mUpdateListener = mUpdateListener;
    }

    public void setSurfaceTexture(SurfaceTexture mSurfaceTexture) {
        this.mSurfaceTexture = mSurfaceTexture;
    }

    public void setSurface(Surface mSurface) {
        this.mSurface = mSurface;
    }

    public RendedWidget(Context context) {
        super(context);
        mCanvas = new Canvas();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //setWillNotDraw(false);
        setDrawingCacheEnabled(false);
        addOnPreDrawListener();
    }


    public RendedWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCanvas = new Canvas();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setWillNotDraw(false);
        setDrawingCacheEnabled(false);
        addOnPreDrawListener();
    }

    public RendedWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCanvas = new Canvas();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setWillNotDraw(false);
        setDrawingCacheEnabled(false);
        addOnPreDrawListener();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            rendViewToOpenGL(canvas);
        } else {
            super.onDraw(canvas);
        }
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void rendViewToOpenGL(Canvas canvas){
        try {
            if (mSurface != null) {
                Canvas surfaceCanvas = mSurface.lockCanvas(null);
                super.dispatchDraw(surfaceCanvas);
                mSurface.unlockCanvasAndPost(surfaceCanvas);

                mSurface.release();
                mSurface = null;
                mSurface = new Surface(mSurfaceTexture);
            }
        } catch (Surface.OutOfResourcesException e) {
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void addOnPreDrawListener() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            final ViewTreeObserver mObserver = getViewTreeObserver();
            if (mObserver != null) {
                mObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        if (isDirty()) {
                            invalidate();
                        }
                        return true;
                    }
                });
            }
        }
    }
}
