package com.zms.imageprocess;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by Administrator on 2015/4/1.
 */
public class PrimaryColorActivity extends Activity {
    private ImageView imageView;
    private SeekBar seekBarHue, seekBarSaturation, seekBarLuminance;
    private float mHue = 0.0f;
    private float mSaturation = 1.0f;
    private float mLuminance = 1.0f;
    private static int MAX_VALUE = 255;
    private static int MID_VALUE = 127;

    private Bitmap bitmap;
    private Button btnReset;
    private int imageFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.primary_color);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(new MyOnClickListener());

        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new MyOnClickListener());

        seekBarHue = (SeekBar) findViewById(R.id.seekBarHue);
        seekBarHue.setMax(MAX_VALUE);
        seekBarHue.setProgress(MID_VALUE);
        seekBarHue.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());

        seekBarSaturation = (SeekBar) findViewById(R.id.seekBarSaturation);
        seekBarSaturation.setMax(MAX_VALUE);
        seekBarSaturation.setProgress(MID_VALUE);
        seekBarSaturation.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());

        seekBarLuminance = (SeekBar) findViewById(R.id.seekBarLuminance);
        seekBarLuminance.setMax(MAX_VALUE);
        seekBarLuminance.setProgress(MID_VALUE);
        seekBarLuminance.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());

    }

    class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            switch (seekBar.getId()) {
                case R.id.seekBarHue:
                    mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE * 180;
                    break;
                case R.id.seekBarSaturation:
                    mSaturation = progress * 1.0F / MID_VALUE;
                    break;
                case R.id.seekBarLuminance:
                    mLuminance = progress * 1.0F / MID_VALUE;
                    break;
            }
            imageView.setImageBitmap(ImageHelper.ImageEffect(bitmap, mHue, mSaturation, mLuminance));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView:
                    if (imageFlag == 0) {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aya);
                        imageView.setImageBitmap(bitmap);
                        imageFlag = 1;
                    } else if (imageFlag == 1) {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
                        imageView.setImageBitmap(bitmap);
                        imageFlag = 0;
                    }
                    // break;
                case R.id.btnReset:
                    imageView.setImageBitmap(ImageHelper.ImageEffect(bitmap, 0.0f, 1.0f, 1.0f));
                    seekBarHue.setProgress(MID_VALUE);
                    seekBarSaturation.setProgress(MID_VALUE);
                    seekBarLuminance.setProgress(MID_VALUE);
                    break;
            }
        }
    }
}
