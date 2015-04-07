package com.zms.imageprocess;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Main extends Activity {
    private Button btnPrimary, btnColorMatrix, btnPixelsEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnPrimary = (Button) findViewById(R.id.btnPrimaryColor);
        btnPrimary.setOnClickListener(new MyOnClickListener());

        btnColorMatrix = (Button) findViewById(R.id.btnColorMatrix);
        btnColorMatrix.setOnClickListener(new MyOnClickListener());

        btnPixelsEffect = (Button) findViewById(R.id.btnPixelsEffect);
        btnPixelsEffect.setOnClickListener(new MyOnClickListener());

    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnPrimaryColor:
                    Intent intent1 = new Intent(Main.this, PrimaryColorActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.btnColorMatrix:
                    Intent intent2 = new Intent(Main.this, ColorMatrixActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.btnPixelsEffect:
                    Intent intent3 = new Intent(Main.this, PixelsEffectActivity.class);
                    startActivity(intent3);
                    break;
            }
        }
    }


}
