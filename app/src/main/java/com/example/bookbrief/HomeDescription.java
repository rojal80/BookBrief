package com.example.bookbrief;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeDescription extends AppCompatActivity {

    TextView txt1;
    TextView txt2;
    TextView likeCountTextView;
    CardView cardHome;
    ImageView heartImage;
    int like= 0;
    boolean isLiked = false;
    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_homedescription);
        getSupportActionBar().hide();
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        heartImage = findViewById(R.id.heartImage);
        likeCountTextView = findViewById(R.id.likeCountTextView);


        txt1.setText(getIntent().getStringExtra("Title"));
        txt2.setText(getIntent().getStringExtra("Description"));


//
//        txt1.setText(title);
//        txt2.setText(description);



        // Set an OnClickListener for the like button
        heartImage.setOnClickListener(v -> {
            // Toggle the like status and update the like count
            like = isLiked ? like - 1 :like+ 1;
            isLiked = !isLiked;
            Toast.makeText(this, "Like clicked", Toast.LENGTH_SHORT).show();

            // Update the like status in your data source (e.g., a database or server)

        });

    }

//    // Method to update the like count in the UI
//    private void updateLikeCountUI() {
//        likeCountTextView.setText(String.valueOf(likeCountTextView));
//
////
//    }



}
