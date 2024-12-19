package com.example.examen3;

import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    String URL = "https://api.jsonbin.io/v3/b/6763118eacd3cb34a8bbdcb1?meta=false";
    String MAMAN = "Maman";
    Vector<String> provenanceVector;
    int nbCadeauParMaman = 0;
    TextView cadeauText;

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

        provenanceVector = new Vector<>();
        cadeauText = findViewById(R.id.maman);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray tab = (JSONArray) response.get("cadeaux");
                            JSONObject element;

                            // ajouter la provenance dans un vecteur
                            for(int i = 0; i < tab.length(); i++){
                                element = tab.getJSONObject(i);
                                String nom = (String) element.get("provenance");
                                provenanceVector.add(nom);
                            }

                            // vérifier le nb de cadeau par Maman
                            for(int j = 0; j < provenanceVector.size(); j++){
                                if(MAMAN.equals(provenanceVector.get(j))) {
                                    nbCadeauParMaman++;
                                }
                            }

                            cadeauText.setText(String.valueOf(nbCadeauParMaman));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Le serveur n'a pas été en mesure de récupérer les valeurs...");
                    }
                });
        queue.add(jsonObjectRequest);
    }
}