package me.zhangls.mediacodecdemo;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by BSDC-ZLS on 2015/3/12.
 */
public class PlayerThread extends Thread {
    private MediaCodec decoder;
    private Surface surface;
    private String url;


    public PlayerThread(Surface surface, String url) {
        this.surface = surface;
        this.url = url;
    }


    @Override
    public void run() {
        MediaExtractor extractor = config(surface);

        if (decoder == null) {
            Log.e("DecodeActivity", "Can't find video info!");
            return;
        }
        decoder.start();

        ByteBuffer[] inputBuffers = decoder.getInputBuffers();
        ByteBuffer[] outputBuffers = decoder.getOutputBuffers();

        boolean isEOS = false;
        long startMs = System.currentTimeMillis();

        while (!Thread.interrupted()) {
            if (!isEOS) {
                /*Returns the index of an input buffer to be filled with valid data
                 or - 1 if no such buffer is currently available.
                  */
                int inIndex = decoder.dequeueInputBuffer(10000);
                if (inIndex >= 0) {
                    ByteBuffer buffer = inputBuffers[inIndex];
                    int sampleSize = extractor.readSampleData(buffer, 0);
                    if (sampleSize < 0) {
                        // We shouldn't stop the playback at this point, just pass the EOS
                        // flag to decoder, we will handleOuput it again from the
                        // dequeueOutputBuffer
                        Log.d("DecodeActivity", "InputBuffer BUFFER_FLAG_END_OF_STREAM");
                        decoder.queueInputBuffer(inIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                        isEOS = true;
                    } else {
                        // After filling a range of the input buffer at the specified index
                        // submit it to the component.
                        decoder.queueInputBuffer(inIndex, 0, sampleSize, extractor.getSampleTime(), 0);
                        // Advance to the next sample.
                        extractor.advance();
                    }
                }
            }
            MediaCodec.BufferInfo info = handleOuput(outputBuffers, startMs, decoder);


            // All decoded frames have been rendered, we can stop playing now
            if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                Log.d("DecodeActivity", "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
                break;
            }
        }


        decoder.stop();
        decoder.release();
        extractor.release();
    }

    private MediaExtractor config(Surface surface) {
        MediaExtractor extractor = new MediaExtractor();
        try {
            extractor.setDataSource(url);
            for (int i = 0; i < extractor.getTrackCount(); i++) {
                MediaFormat format = extractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                if (mime.startsWith("video/")) {
                    extractor.selectTrack(i);
                    decoder = MediaCodec.createDecoderByType(mime);
                    decoder.configure(format, surface, null, 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractor;
    }

    private MediaCodec.BufferInfo handleOuput(ByteBuffer[] outputBuffers, long startMs, MediaCodec mediaCodec) {
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        //Dequeue an output buffer, block at most "timeoutUs" microseconds.
        int outIndex = mediaCodec.dequeueOutputBuffer(info, 10000);
        switch (outIndex) {
            case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                Log.d("DecodeActivity", "INFO_OUTPUT_BUFFERS_CHANGED");
                outputBuffers = decoder.getOutputBuffers();
                break;
            case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                Log.d("DecodeActivity", "New format " + mediaCodec.getOutputFormat());
                break;
            case MediaCodec.INFO_TRY_AGAIN_LATER:
                Log.d("DecodeActivity", "dequeueOutputBuffer timed out!");
                break;
            default:
                ByteBuffer buffer = outputBuffers[outIndex];
                Log.v("DecodeActivity", "We can't use this buffer but render it due to the API limit, " + buffer);


                // We use a very simple clock to keep the video FPS, or the video
                // playback will be too fast
                while (info.presentationTimeUs / 1000 > System.currentTimeMillis() - startMs) {
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                // If you are done with a buffer, use this call to return the buffer to the codec.
                mediaCodec.releaseOutputBuffer(outIndex, true);
                break;
        }
        return info;
    }
}
