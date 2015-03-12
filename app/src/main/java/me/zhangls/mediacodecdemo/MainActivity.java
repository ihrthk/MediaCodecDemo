package me.zhangls.mediacodecdemo;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainActivity extends Activity implements SurfaceHolder.Callback {

    private PlayerThread mPlayer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceView sv = new SurfaceView(this);
        sv.getHolder().addCallback(this);
        setContentView(sv);
    }


    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mPlayer == null) {
            String SAMPLE = Environment.getExternalStorageDirectory() + "/video.mp4";
            String url = "http://mvvideo2.meitudata.com/5450886dddd8e2048.mp4";
            mPlayer = new PlayerThread(holder.getSurface(), url);
            mPlayer.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mPlayer != null) {
            mPlayer.interrupt();
        }
    }

}