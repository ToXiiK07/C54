package com.example.annexe9;

import android.os.Bundle;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Configurer les insets pour un affichage bord à bord
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String url = "https://api.jsonbin.io/v3/b/6733b233ad19ca34f8c9149a?meta=false";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // contenu du header
                            JSONObject menu = response.getJSONObject("menu");
                            String header = (String) menu.get("header");
                            System.out.println("Contenu du header " + header);

                            // nb éléments dans item
                            JSONArray itemsArray = menu.getJSONArray("items");
                            int itemCount = itemsArray.length();
                            System.out.println("Nombre d'éléments dans items : " + itemCount);

                            // nb élément à null dans item
                            int isNull = 0;
                            for(int i = 0; i < itemsArray.length(); i++){
                                if(itemsArray.isNull(i)){
                                    isNull++;
                                }
                            }
                            System.out.println("Nombre d'éléments dans items à null " + isNull);

                            // nb élément qui a pas label
                            int isNotLabel = 0;
                            for(int i = 0; i < itemsArray.length(); i++){
                                if(!itemsArray.isNull(i)) {
                                    if (!itemsArray.getJSONObject(i).has("label")) {
                                        isNotLabel++;
                                    }
                                }
                            }
                            System.out.println("Nombre d'éléments dans items qui on pas label " + isNotLabel);




                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(jsonObjectRequest);
    }
}
