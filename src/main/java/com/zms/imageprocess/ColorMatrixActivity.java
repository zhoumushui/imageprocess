package com.zms.imageprocess;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/4/6.
 */
public class ColorMatrixActivity extends Activity {
    private ImageView mImageView;
    private GridLayout matrixGroup;
    private Bitmap bitmap;
    private Button btnChange, btnReset;

    private int mEtWidth, mEtHeight;
    private EditText[] mEts = new EditText[20];
    private float[] mColorMatrix = new float[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_matrix);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aya);
        mImageView = (ImageView) findViewById(R.id.imageView);
        matrixGroup = (GridLayout) findViewById(R.id.matrixGroup);
        mImageView.setImageBitmap(bitmap);

        btnChange = (Button) findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new MyOnClickListener());
        btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new MyOnClickListener());


        matrixGroup.post(new Runnable() {
            @Override
            public void run() {
                mEtWidth = matrixGroup.getWidth() / 5;
                mEtHeight = matrixGroup.getHeight() / 4;
                initEditText();
                initMatrix();
            }
        });
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnChange:
                    getMatrix();
                    setImageMatrix();
                    break;
                case R.id.btnReset:
                    initMatrix();
                    getMatrix();
                    setImageMatrix();
                    break;
            }
        }
    }

    private void getMatrix() {
        for (int i = 0; i < 20; i++) {
            mColorMatrix[i] = Float.valueOf(mEts[i].getText().toString());
        }
    }

    private void setImageMatrix() {
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        android.graphics.ColorMatrix colorMatrix = new android.graphics.ColorMatrix();
        colorMatrix.set(mColorMatrix);

        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        mImageView.setImageBitmap(bmp);
    }

    private void initEditText() {
        for (int i = 0; i < 20; i++) {
            EditText editText = new EditText(ColorMatrixActivity.this);
            mEts[i] = editText;
            matrixGroup.addView(editText, mEtWidth, mEtHeight);
        }
    }

    private void initMatrix() {
        for (int i = 0; i < 20; i++) {
            if (i % 6 == 0) {
                mEts[i].setText(String.valueOf(1));
            } else {
                mEts[i].setText(String.valueOf(0));
            }
        }
    }
}
