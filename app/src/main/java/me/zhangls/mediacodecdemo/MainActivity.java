package me.zhangls.mediacodecdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class MainActivity extends Activity {


    String SAMPLE = Environment.getExternalStorageDirectory() + "/video.mp4";
    public static final String URL = "URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void surface_view(View view) {
        Intent intent = new Intent(this, SurfaceViewActivity.class);
        intent.putExtra(URL, "http://mvvideo2.meitudata.com/5450886dddd8e2048.mp4");
        startActivity(intent);
    }

    public void texture_view(View view) {
        Intent intent = new Intent(this, TextureViewActivity.class);
        intent.putExtra(URL, "http://mvvideo2.meitudata.com/5450886dddd8e2048.mp4");
        startActivity(intent);

    }
}