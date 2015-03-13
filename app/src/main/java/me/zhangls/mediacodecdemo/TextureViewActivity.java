package me.zhangls.mediacodecdemo;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by BSDC-ZLS on 2015/3/13.
 */
public class TextureViewActivity extends Activity implements TextureView.SurfaceTextureListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textureview);

        TextureView textureView = (TextureView) findViewById(R.id.texture_view);
        textureView.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Surface sur = new Surface(surface);
        String url = getIntent().getStringExtra(MainActivity.URL);
        new PlayerThread(sur, url).start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
