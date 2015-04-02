package com.zms.imageprocess;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by Administrator on 2015/4/1.
 */
public class ImageHelper {
    // hue-色相 saturation-饱和度 lum-亮度
    public static Bitmap ImageEffect(Bitmap bitmap, float hue, float saturation, float luminance) {

        Bitmap mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Hue-色相/色调
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue); // RED
        hueMatrix.setRotate(1, hue); // GREEN
        hueMatrix.setRotate(2, hue); // BLUE

        // Saturation-饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        // Luminance-亮度/明度
        ColorMatrix luminanceMatrix = new ColorMatrix();
        //  setScale(float rScale, float gScale, float bScale, float aScale)
        luminanceMatrix.setScale(luminance, luminance, luminance, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(luminanceMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        // drawBitmap(@NonNull Bitmap bitmap, float left, float top, @Nullable Paint paint)
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return mBitmap;
    }
}
