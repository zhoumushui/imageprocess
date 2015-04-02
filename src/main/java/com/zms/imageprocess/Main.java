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
    private Button btnPrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnPrimary = (Button) findViewById(R.id.btnPrimary);
        btnPrimary.setOnClickListener(new MyOnClickListener());
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnPrimary:
                    Intent intent1 = new Intent(Main.this, PrimaryColorActivity.class);
                    startActivity(intent1);
                    break;
            }
        }
    }


}
