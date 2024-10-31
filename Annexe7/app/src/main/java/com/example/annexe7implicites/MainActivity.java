package com.example.annexe7implicites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button buttonAppelerMarie, buttonHawkesbury, buttonPhoto, buttonMessage;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActivityResultLauncher<Intent> launcher;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Bundle extras = result.getData().getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        });

        buttonAppelerMarie = findViewById(R.id.boutonAppel);
        buttonHawkesbury = findViewById(R.id.boutonVille);
        buttonPhoto = findViewById(R.id.boutonPhoto);
        buttonMessage = findViewById(R.id.boutonMessage);
        imageView = findViewById(R.id.imageView);

        // interface fonctionnelle a juste UNE méthode dedans genre onClick

        buttonAppelerMarie.setOnClickListener(source -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4505595636"));
            startActivity(intent);
        });

        buttonHawkesbury.setOnClickListener(source -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Hawkesbury, +Ontario, +Canada "));
            startActivity(intent);
        });

        buttonPhoto.setOnClickListener(source -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            launcher.launch(intent);
        });

    }
}