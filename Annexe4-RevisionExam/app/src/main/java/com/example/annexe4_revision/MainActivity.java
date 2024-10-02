package com.example.annexe4_revision;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Ecouteur ec;
    Button connaitre;
    TextView nomPrenom;
    ActivityResultLauncher<Intent> launcher;
    Utilisateur utilisateur;

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

        ec = new Ecouteur();


        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityCallBack());

        connaitre = findViewById(R.id.connaitre);
        nomPrenom = findViewById(R.id.bonjour);

        connaitre.setOnClickListener(ec);

        if(savedInstanceState != null){
            utilisateur = (Utilisateur) savedInstanceState.getSerializable("user");
            nomPrenom.setText("Bonjour " + utilisateur.getNom() + " " + utilisateur.getPrenom());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("user", utilisateur);
    }


    private class ActivityCallBack implements ActivityResultCallback<ActivityResult> {
        @Override
        public void onActivityResult(ActivityResult o) {
            Utilisateur temp = (Utilisateur) o.getData().getSerializableExtra("user");
            utilisateur = new Utilisateur(temp.getNom(), temp.getPrenom());
            nomPrenom.setText("Bonjour " + utilisateur.getPrenom() + " " + utilisateur.getNom());
        }
    }



    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v == connaitre) {
                Intent intent = new Intent(MainActivity.this, NomPrenom.class);
                launcher.launch(intent);
            }
        }
    }
}