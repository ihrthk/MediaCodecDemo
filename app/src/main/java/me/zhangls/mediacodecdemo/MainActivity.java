package me.zhangls.mediacodecdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void surface_view(View view) {
        startActivity(new Intent(this, SurfaceViewActivity.class));
    }

    public void texture_view(View view) {
        startActivity(new Intent(this, TextureViewActivity.class));

    }
}