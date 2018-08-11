package com.render.demo;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.Surface.OutOfResourcesException;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

public class GLProgressBar extends ProgressBar {

	private int textureHeight = 200;
	private int textureWidth = 200;

	private Surface mSurface;
	
	private SurfaceTexture mSurfaceTexture;

	public void setSurfaceTexture(SurfaceTexture mSurfaceTexture) {
		this.mSurfaceTexture = mSurfaceTexture;
	}

	public void setSurface(Surface mSurface) {
		this.mSurface = mSurface;
	}

	public int getTextureHeight() {
		return textureHeight;
	}

	public void setTextureHeight(int textureHeight) {
		this.textureHeight = textureHeight;
	}

	public int getTextureWidth() {
		return textureWidth;
	}

	public void setTextureWidth(int textureWidth) {
		this.textureWidth = textureWidth;
	}

	public GLProgressBar(Context context) {
		super(context);
		setLayoutParams(new LayoutParams(200, 200));
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
		super.onDraw( canvas ); // <- Uncomment this if you want to show the
		//original view
	}
}
