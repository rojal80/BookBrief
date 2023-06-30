package com.example.bookbrief;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeDescription extends AppCompatActivity {
    ImageView img;
    TextView txt1,txt2;
    CardView cardHome;
    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_homedescription);
        getSupportActionBar().hide();
        img=findViewById(R.id.img);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);


        img.setImageResource(getIntent().getIntExtra("imagename",0));
        txt1.setText(getIntent().getStringExtra("name"));
        txt2.setText(getIntent().getStringExtra("description"));

    }
}
