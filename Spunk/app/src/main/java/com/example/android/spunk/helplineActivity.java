package com.example.android.spunk;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class helplineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);
    }

    public void callCrisis(View view){
        TextView tv = (TextView) findViewById(view.getId());
        String phoneNo = tv.getText().toString();

            String dial = "tel:" + phoneNo;
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));

    }
    public void openHelplineLink(View view){
        String url = "https://nobullying.com/bullying-help-usa/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
