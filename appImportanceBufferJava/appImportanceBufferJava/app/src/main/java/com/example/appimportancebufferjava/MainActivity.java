package com.example.appimportancebufferjava;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Button bouton;
    TextView texteDuree, texteNom;
    ActivityResultLauncher<Intent> lanceur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bouton = findViewById(R.id.bouton);
        texteDuree = findViewById(R.id.texteDuree);
        texteNom = findViewById(R.id.texteNom);
        Ecouteur ec = new Ecouteur();
        bouton.setOnClickListener(ec);

        // création du lanceur de boomerang, objet sera appelé au retour du boomerang dans cette classe
        lanceur = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CallBackMusic());
    }


    private class CallBackMusic implements ActivityResultCallback<ActivityResult> {


        // appelé quand je reviens du choix de fichiers sur le téléphone dans cette activité, retour du boomerang
        @Override
        public void onActivityResult(ActivityResult result) {


            if (result.getData() != null) { // si l'utilisateur a choisi un fichier
                Uri uri = result.getData().getData(); // données retournées par l'Intent
                ContentResolver resolver = getContentResolver();// objet permettant d'accéder aux données sur le téléphones ( méthodes CRUD ), présente les données sous forme de tables

                //nom du fichier
                Cursor cursor = resolver.query(uri, new String[]{OpenableColumns.DISPLAY_NAME}, null, null, null);
                cursor.moveToFirst();
                texteNom.setText(cursor.getString(0));


                try {
                    // ouvrir un flux de données vers l'URI choisi
                    InputStream stream = resolver.openInputStream(uri); //c'est un stream d'octets

                    texteDuree.setText("durée : " + tempsDeLecture(stream) + "ms");
                } catch (Exception fnf) { // fichier non trouvé
                    fnf.printStackTrace();
                }
            }

        }
    }


    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            rechercherFichiers();
        }
    }


    public void rechercherFichiers() {
        // intent vers le téléphone
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/*");  // ou text/* // fichiers musciaux
        lanceur.launch(intent);  // lance l'intent différemment qu'avec startActivity car on attend un résultat dans cette activité, affiche les fichiers musicaux
    }


    public String tempsDeLecture(InputStream chemin) throws IOException {
        //à faire
        long duree = 0;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = (FileInputStream) chemin;
            bis = new BufferedInputStream(fis);
            long debut = System.currentTimeMillis();
            while (bis.read() != -1);
            long fin = System.currentTimeMillis();
            duree = fin - debut;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (bis != null) {
                bis.close();
            }
        }

        return String.valueOf(duree);
    }



}