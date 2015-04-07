package com.zms.imageprocess;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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


    public static Bitmap handleImage(Bitmap bm, int effect) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color;
        int r, g, b, a, r1, g1, b1;
        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        bm.getPixels(oldPx, 0, width, 0, 0, width, height);
        switch (effect) {

            case 0: // 负片效果
                for (int i = 0; i < width * height; i++) {
                    color = oldPx[i];
                    r = Color.red(color);
                    g = Color.green(color);
                    b = Color.blue(color);
                    a = Color.alpha(color);

                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;

                    if (r > 255) {
                        r = 255;
                    } else if (r < 0) {
                        r = 0;
                    }
                    if (g > 255) {
                        g = 255;
                    } else if (g < 0) {
                        g = 0;
                    }
                    if (b > 255) {
                        b = 255;
                    } else if (b < 0) {
                        b = 0;
                    }
                    newPx[i] = Color.argb(a, r, g, b);
                }
                bmp.setPixels(newPx, 0, width, 0, 0, width, height);
                break;
            case 1: // 怀旧效果
                bm.getPixels(oldPx, 0, bm.getWidth(), 0, 0, width, height);
                for (int i = 0; i < width * height; i++) {
                    color = oldPx[i];
                    a = Color.alpha(color);
                    r = Color.red(color);
                    g = Color.green(color);
                    b = Color.blue(color);

                    r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                    g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                    b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                    if (r1 > 255) {
                        r1 = 255;
                    }
                    if (g1 > 255) {
                        g1 = 255;
                    }
                    if (b1 > 255) {
                        b1 = 255;
                    }
                    newPx[i] = Color.argb(a, r1, g1, b1);
                }
                bmp.setPixels(newPx, 0, width, 0, 0, width, height);
                break;
            case 2: // 浮雕效果
                color = 0;
                int colorBefore = 0;
                for (int i = 1; i < width * height; i++) {
                    colorBefore = oldPx[i - 1];
                    a = Color.alpha(colorBefore);
                    r = Color.red(colorBefore);
                    g = Color.green(colorBefore);
                    b = Color.blue(colorBefore);

                    color = oldPx[i];
                    r1 = Color.red(color);
                    g1 = Color.green(color);
                    b1 = Color.blue(color);

                    r = (r - r1 + 127);
                    g = (g - g1 + 127);
                    b = (b - b1 + 127);
                    if (r > 255) {
                        r = 255;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                    newPx[i] = Color.argb(a, r, g, b);
                }
                bmp.setPixels(newPx, 0, width, 0, 0, width, height);
                break;
        }
        return bmp;
    }

}
