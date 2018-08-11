package com.render.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class GLActivity extends Activity {

    public static final int APPWIDGET_HOST_ID = 1001;
    public static final int REQUEST_BIND_APPWIDGET = 1003;
    private static final int REQUEST_CREATE_SHORTCUT = 1;
    private static final int REQUEST_CREATE_APPWIDGET = 5;

    private int[] padding = new int[]{0, 0, 0};
    private ArrayList<Object> mWidgets;
    private BitmapDrawable previewDrawable;

    private int screenHeight;
    private int screenWidth;
    private int maxWidth;
    private int maxHeight;

    private LinearLayout root;

    private RendedWidget rendedWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the activity to display the glSurfaceView
        setContentView(R.layout.activity_main);

        root = (LinearLayout) findViewById(R.id.root);

        initScreenSize();
        rendedWidget = new RendedWidget(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        rendedWidget.setLayoutParams(lp);

    }


    private void initScreenSize() {
        Display screenSize = getWindowManager().getDefaultDisplay();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();
        maxWidth = maxHeight = screenWidth >= screenHeight ? screenHeight >> 1 : screenWidth >> 1;
    }

    private boolean isAdded = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (!isAdded)
            addWidgetView(rendedWidget);
    }

    public final static int FINSISH_LOAD_WIDGET = 1;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FINSISH_LOAD_WIDGET:
                    break;
            }
        }
    };


    /**
     * @param mHostView
     */
    @SuppressLint("NewApi")
    private void addWidgetView(final RendedWidget mHostView) {

        Display mDisplay = getWindowManager().getDefaultDisplay();
        GLProgressBar progressBar = new GLProgressBar(this);

        CustomRenderer renderer = new CustomRenderer(getApplicationContext(), progressBar, mDisplay);
        GLSurfaceView glSurfaceView = new GLSurfaceView(getApplicationContext());
        // Setup the surface view for drawing to

        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        glSurfaceView.setRenderer(renderer);
        //glSurfaceView.setZOrderOnTop(true);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
        // Add our WebView to the Android View hierarchy
        glSurfaceView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
        root.addView(glSurfaceView);
        root.addView(progressBar, lp);
        //addContentView(glProgressBar, new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        isAdded = true;
    }


}
