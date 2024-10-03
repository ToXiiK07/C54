package com.example.atelier2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {


    Vector<Hashtable<String, Object>> vector = new Vector<Hashtable<String, Object>>();
    ListView listView;

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


        listView = findViewById(R.id.liste);

        String url = "https://api.jsonbin.io/v3/b/637056232b3499323bfe110a?meta=false";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                        // JSONObject response

                        try {
                            JSONArray tab = (JSONArray) response.get("articles");
                            JSONObject le_premier;
                            for(int i = 0; i < tab.length(); i++){
                                le_premier = tab.getJSONObject(i);

                                String prix = (String) le_premier.get("prix");
                                String nom = (String) le_premier.get("nom");

                                System.out.println("Prix : " + prix);
                                System.out.println("Article : " + nom);

                                Hashtable<String, Object> temp = new Hashtable<>();
                                temp.put("nom", nom);
                                temp.put("prix", prix);
                                vector.add(temp);
                            }


                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), vector, R.layout.produit, new String[]{"nom", "prix"}, new int[]{R.id.produit, R.id.prix});
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        queue.add(jsonObjectRequest);
    }
}