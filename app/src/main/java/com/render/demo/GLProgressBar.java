package com.render.demo;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.Surface.OutOfResourcesException;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class GLProgressBar extends ProgressBar implements IRendedView{

	private Surface mSurface;
	
	private SurfaceTexture mSurfaceTexture;


	@Override
	public void configSurface(Surface surface) {
		this.mSurface = surface;
	}

	@Override
	public void configSurfaceTexture(SurfaceTexture surfaceTexture) {
		this.mSurfaceTexture = surfaceTexture;
	}

	public GLProgressBar(Context context) {
		super(context);
		setLayoutParams(new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 400));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mSurface != null) {
			// Requires a try/catch for .lockCanvas( null )
			try {
				final Canvas surfaceCanvas = mSurface.lockCanvas(null); // Android
				// canvas
				// from
				// surface
				super.onDraw(surfaceCanvas); // Call the WebView onDraw
				// targetting the canvas
				mSurface.unlockCanvasAndPost(surfaceCanvas); // We're done with
				// the canvas!
			} catch (OutOfResourcesException excp) {
				excp.printStackTrace();
			}
		}
		
		if (mSurface != null) {
			mSurface.release();
			mSurface = null;
			mSurface = new Surface(mSurfaceTexture);
		}
		
		// original view
		super.onDraw( canvas ); // <- Uncomment this if you want to show the original view
	}
}
