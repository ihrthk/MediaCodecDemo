package me.zhangls.mediacodecdemo;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by BSDC-ZLS on 2015/3/23.
 */
public class SurfaceViewStrategy implements VideoStrategy, SurfaceHolder.Callback {
    private Context context;
    private SurfaceView surfaceView;
    private SurfaceListener listener;


    public SurfaceViewStrategy(ViewGroup viewGroup, SurfaceListener listener) {
        this.context = context;
        this.listener = listener;

        surfaceView = new SurfaceView(viewGroup.getContext());
        surfaceView.getHolder().addCallback(this);
        viewGroup.addView(surfaceView);
    }

    @Override
    public View getWidget() {
        return surfaceView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        listener.onSurface(holder.getSurface(), false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
