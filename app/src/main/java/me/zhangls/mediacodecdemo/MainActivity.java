package me.zhangls.mediacodecdemo;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;


public class MainActivity extends Activity implements SurfaceListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String URL = "http://mvvideo2.meitudata.com/5450886dddd8e2048.mp4";
    private boolean useTextureView = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;

    String SAMPLE = Environment.getExternalStorageDirectory() + "/video.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        setContentView(view);

        VideoStrategy videoStrategy = useTextureView ?
                new TextureViewStrategy((ViewGroup) view, this) :
                new SurfaceViewStrategy((ViewGroup) view, this);
    }

    @Override
    public void onSurface(Surface surface, boolean isTextureView) {
        Log.e(TAG, "isTextureView:" + isTextureView);
        PlayerThread mPlayer = new PlayerThread(surface, URL);
        mPlayer.start();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}