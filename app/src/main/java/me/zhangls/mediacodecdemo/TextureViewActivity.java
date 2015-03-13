package me.zhangls.mediacodecdemo;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by BSDC-ZLS on 2015/3/13.
 */
public class TextureViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textureview);


        TextureView textureView = (TextureView) findViewById(R.id.texture_view);

        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        Surface surface = new Surface(surfaceTexture);
        new PlayerThread(surface, "http://mvvideo2.meitudata.com/5450886dddd8e2048.mp4").start();
    }
}
