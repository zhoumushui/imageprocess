package com.zms.imageprocess;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

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
        Bitmap bitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Bitmap.Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0;
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
                bitmap.setPixels(newPx, 0, width, 0, 0, width, height);
                break;
            case 1: // 怀旧效果
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
                bitmap.setPixels(newPx, 0, width, 0, 0, width, height);
                break;
            case 2: // 浮雕效果
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
                bitmap.setPixels(newPx, 0, width, 0, 0, width, height);
                break;
            case 3: // 灰度
                int alpha = 0xFF << 24;
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        int grey = oldPx[width * i + j];
                        int red = ((grey & 0x00FF0000) >> 16);
                        int green = ((grey & 0x0000FF00) >> 8);
                        int blue = (grey & 0x000000FF);
                        grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                        grey = alpha | (grey << 16) | (grey << 8) | grey;
                        newPx[width * i + j] = grey;
                    }
                }
                bitmap.setPixels(newPx, 0, width, 0, 0, width, height);
                break;
            case 4: // 圆角
                int roundPx = 55;
                Canvas canvas = new Canvas(bitmap);

                int colorRound = 0xff424242;
                Paint paint = new Paint();
                Rect rect = new Rect(0, 0, bm.getWidth(), bm.getHeight());
                RectF rectF = new RectF(rect);

                paint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                paint.setColor(colorRound);
                canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bm, rect, rect, paint);
                break;
            case 5: // 油画
                int Radio = 0;
                Random rnd = new Random();
                int iModel = 10;
                int i = width - iModel;
                while (i > 1) {
                    int j = height - iModel;
                    while (j > 1) {
                        int iPos = rnd.nextInt(10) % iModel;
                        color = bm.getPixel(i + iPos, j + iPos);
                        bitmap.setPixel(i, j, color);
                        j = j - 1;
                    }
                    i = i - 1;
                }
                break;
            case 6: // 左右对称
                Canvas canvas2 = new Canvas(bitmap);
                canvas2.drawColor(Color.BLACK);
                canvas2.drawBitmap(bm, 0, 0, null);
                Matrix matrix = new Matrix();
                float[] values = {-1f, 0.0f, 0.0f, 0.0f, 1f, 0.0f, 0.0f, 0.0f, 1.0f};
                matrix.setValues(values);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
                        matrix, true);
                canvas2.drawBitmap(bitmap, bm.getWidth(), 0, null);
                break;
        }
        return bitmap;
    }

}
