package com.zms.imageprocess;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/4/6.
 */
public class PixelsEffectActivity extends Activity {
    private ImageView imageView;
    private Button btnRaw, btnNegative, btnOld, btnRelief;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pixels_effect);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aya);
        imageView = (ImageView) findViewById(R.id.imageView);

        btnRaw = (Button) findViewById(R.id.btnRaw);
        btnNegative = (Button) findViewById(R.id.btnNegative);
        btnOld = (Button) findViewById(R.id.btnOld);
        btnRelief = (Button) findViewById(R.id.btnRelief);

        btnRaw.setOnClickListener(new MyOnClickListener());
        btnNegative.setOnClickListener(new MyOnClickListener());
        btnOld.setOnClickListener(new MyOnClickListener());
        btnRelief.setOnClickListener(new MyOnClickListener());
        imageView.setImageBitmap(bitmap);
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnRaw:
                    imageView.setImageBitmap(bitmap);
                    break;
                case R.id.btnNegative:
                    imageView.setImageBitmap(ImageHelper.handleImage(bitmap, 0));
                    break;
                case R.id.btnOld:
                    imageView.setImageBitmap(ImageHelper.handleImage(bitmap, 1));
                    break;
                case R.id.btnRelief:
                    imageView.setImageBitmap(ImageHelper.handleImage(bitmap, 2));
                    break;
            }
        }
    }
}
