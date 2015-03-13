package me.zhangls.mediacodecdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by BSDC-ZLS on 2015/3/13.
 */
public class SurfaceViewActivity extends Activity implements SurfaceHolder.Callback {

    private PlayerThread mPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surfaceview);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        surfaceView.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mPlayer == null) {
            String url = getIntent().getStringExtra(MainActivity.URL);
            mPlayer = new PlayerThread(holder.getSurface(), url);
            mPlayer.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mPlayer != null) {
            mPlayer.interrupt();
        }
    }
}
