package com.example.pratique_examen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.util.Scanner;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Button buttonSave;
    EditText champNom;
    EditText champAge;
    TextView infoUser;
    Singleton singleton;
    Vector<Planete> planete;

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

        planete = new Vector<>();

        buttonSave = findViewById(R.id.buttonSave);
        champNom = findViewById(R.id.editTextName);
        champAge = findViewById(R.id.editTextAge);
        infoUser = findViewById(R.id.textViewOutput);

    // ----- SÉRIALISER - DÉSIRIALISER -------

        singleton = Singleton.getInstance(getApplicationContext());

        try {
            singleton.deserialiserListe();
            Personne userData = singleton.getUserData();
            if (userData != null) {
                champNom.setText(userData.getNom());
                champAge.setText(String.valueOf(userData.getAge()));
                infoUser.setText("Nom : " + userData.getNom() + "\nÂge : " + userData.getAge());
            }
        } catch (Exception e) {
            e.printStackTrace();
            infoUser.setText("Aucune donnée sauvegardée.");
        }

        buttonSave.setOnClickListener(source -> {
            String nom = champNom.getText().toString();
            int age = Integer.parseInt(champAge.getText().toString());

            singleton.setUserData(nom, age);

            try {
                singleton.serialiserListe();
                infoUser.setText("Données sauvegardées !\nNom : " + nom + "\nÂge : " + age);
            } catch (Exception e) {
                e.printStackTrace();
                infoUser.setText("Erreur lors de la sauvegarde.");
            }
        });

        // ----- VOLLEY - GSON - JSON --------

        String URL = "https://jsonplaceholder.typicode.com/users";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject user = response.getJSONObject(i);

                                String name = user.getString("name");
                                System.out.println(name);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println("Erreur : " + error.getMessage());
                    }
                });

        queue.add(jsonArrayRequest);


        // ---------- FICHIER .TXT ----------------
        listePlanetes();
        for(int j = 0; j < planete.size(); j++){
            System.out.println(planete.get(j));
        }

    }

    public void listePlanetes() {
        String nomPlanete = "";
        int nombreSatellites = 0;

        try {
            FileInputStream fis = openFileInput("planete.txt");
            Scanner sc = new Scanner(fis);

            sc.useDelimiter(",");

            while (sc.hasNext()) {
                nomPlanete = sc.next();

                nombreSatellites = sc.nextInt();


                Planete p = new Planete(nomPlanete, nombreSatellites);
                planete.add(p);
            }

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Planete {
        String nom;
        int satellites;

        public Planete(String nom, int satellites) {
            this.nom = nom;
            this.satellites = satellites;
        }

    }
}