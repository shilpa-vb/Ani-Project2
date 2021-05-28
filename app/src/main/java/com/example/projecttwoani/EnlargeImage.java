package com.example.projecttwoani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class EnlargeImage extends AppCompatActivity {

    GridImages_Class images_class;
    ImageView enlargeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_image);

        enlargeImage = findViewById(R.id.enlarge_image);

        Intent intent_img = getIntent();
        String enlarge = intent_img.getExtras().getString("clicked");
//        Log.i("enlar", enlarge);
        Picasso.get()
                .load(enlarge)
                .into(enlargeImage);
    }
}