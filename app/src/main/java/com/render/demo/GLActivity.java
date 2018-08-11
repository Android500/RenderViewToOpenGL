package com.render.demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;
import android.widget.FrameLayout;

public class GLActivity extends Activity {


    private FrameLayout root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the activity to display the glSurfaceView
        setContentView(R.layout.activity_main);
        root = (FrameLayout) findViewById(R.id.root);
        addView();
    }


    private void addView() {

        Display mDisplay = getWindowManager().getDefaultDisplay();
        GLProgressBar glProgressBar = new GLProgressBar(this);

        ViewRenderer renderer = new ViewRenderer(getApplicationContext(), glProgressBar, mDisplay);
        GLSurfaceView glSurfaceView = new GLSurfaceView(getApplicationContext());
        // Setup the surface view for drawing to

        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glSurfaceView.setRenderer(renderer);
        //glSurfaceView.setZOrderOnTop(true);
        // Add our WebView to the Android View hierarchy
        glSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        root.addView(glSurfaceView);
        root.addView(glProgressBar);
        //addContentView(glProgressBar, new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

}
