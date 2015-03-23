package me.zhangls.mediacodecdemo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by BSDC-ZLS on 2015/3/23.
 */
public class TextureViewStrategy implements VideoStrategy, TextureView.SurfaceTextureListener {

    private Context context;
    private SurfaceListener listener;

    public TextureViewStrategy(ViewGroup viewGroup, SurfaceListener listener) {
        this.context = context;
        this.listener = listener;

        TextureView textureView = new TextureView(viewGroup.getContext());
        textureView.setSurfaceTextureListener(this);
        viewGroup.addView(textureView);
    }

    @Override
    public View getWidget() {
        return null;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Surface sur = new Surface(surface);
        listener.onSurface(sur, true);
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
