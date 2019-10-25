package org.gian.gian;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button abt_ev,form,exit_bt,web_view;

        abt_ev = (Button) findViewById(R.id.abt_event);
        form = (Button) findViewById(R.id.form_btn);
        exit_bt = (Button) findViewById(R.id.exit_btn);
        web_view = (Button) findViewById(R.id.webview_act);

        abt_ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abt = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(abt);
            }
        });

        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent abt = new Intent(MainActivity.this,FormActivity.class);
                startActivity(abt);
            }
        });

        exit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        web_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wbview = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(wbview);
            }
        });
    }
}
