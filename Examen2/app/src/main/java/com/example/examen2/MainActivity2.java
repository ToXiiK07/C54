package com.example.examen2;

import static android.animation.ObjectAnimator.ofFloat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

public class MainActivity2 extends AppCompatActivity {

    ImageView mongolfiere;
    Button enregister;
    EditText rep;
    ObjectAnimator monteY, droiteX, objectAnimator, objectAnimator2, objectAnimator3, objectAnimator4;
    AnimatorSet animatorSetPrincipal, animatorSet1, animatorSet2, animatorSet3, animatorSetVictoire, animatorSet;
    boolean q2, q3 = false;
    boolean q1 = true;

    Vector<AnimatorSet> listeAnimation;
    String URL = "https://api.jsonbin.io/v3/b/673cf708acd3cb34a8ab4e3c?meta=false";
    Vector<String> stringVector;
    Vector<String> dejaDit;
    String valeurReponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mongolfiere = findViewById(R.id.imageView);
        enregister = findViewById(R.id.button);
        rep = findViewById(R.id.editTextText);
        stringVector = new Vector<>();
        dejaDit = new Vector<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray tab = (JSONArray) response.get("items");
                            JSONObject le_premier;
                            for(int i = 0; i < tab.length(); i++){
                                le_premier = tab.getJSONObject(i);
                                String nom = (String) le_premier.get("name");

                                stringVector.add(nom);
                            }
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

        //  ----- QUESTION 3 -----

        listeAnimation = new Vector<>();
        animatorSetPrincipal = new AnimatorSet();
        animatorSet = new AnimatorSet();
        animatorSet1 = new AnimatorSet();
        animatorSet2 = new AnimatorSet();
        animatorSet3 = new AnimatorSet();
        animatorSetVictoire = new AnimatorSet();

        monteY = ofFloat(mongolfiere, View.TRANSLATION_Y, -300);
        monteY.setDuration(2000);
        droiteX = ofFloat(mongolfiere, View.TRANSLATION_X, 300);
        droiteX.setDuration(2000);
        animatorSet1.playTogether(monteY, droiteX);

        listeAnimation.add(animatorSet1);

        objectAnimator = ofFloat(mongolfiere, View.TRANSLATION_Y, -500);
        objectAnimator.setDuration(2000);
        objectAnimator2 = ofFloat(mongolfiere, View.TRANSLATION_X, -100);
        objectAnimator2.setDuration(2000);
        animatorSet2.playTogether(objectAnimator, objectAnimator2);

        listeAnimation.add(animatorSet2);

        objectAnimator3 = ofFloat(mongolfiere, View.TRANSLATION_X, -25);
        objectAnimator3.setDuration(2000);
        objectAnimator4 = ofFloat(mongolfiere, View.TRANSLATION_Y, -1000);
        objectAnimator4.setDuration(2000);
        animatorSet3.playTogether(objectAnimator3, objectAnimator4);

        ObjectAnimator animationVictoireScaleX = ofFloat(mongolfiere, View.SCALE_X, 500);
        animationVictoireScaleX.setDuration(5000);
        ObjectAnimator animationVictoireScaleY = ofFloat(mongolfiere, View.SCALE_Y, 500);
        animationVictoireScaleY.setDuration(5000);
        ObjectAnimator animationVictoireCouleur = ObjectAnimator.ofFloat(mongolfiere, View.ALPHA, 1, 0);
        animationVictoireCouleur.setDuration(5000);
        animatorSetVictoire.playTogether(animationVictoireScaleX, animationVictoireScaleY, animationVictoireCouleur);

        listeAnimation.add(animatorSet3);


        enregister.setOnClickListener(source -> {
            valeurReponse = String.valueOf(rep.getText());
            for(int i = 0; i < stringVector.size(); i++) {
                if(valeurReponse.equals(stringVector.get(i))){

                    // En commentaire parce que ça fait buster la mémoire quand je vérifie si le nom est pareil

//                    dejaDit.add(valeurReponse);
//                    if (dejaDit.size() > 1) {
//                        i = 0;
//                        if(valeurReponse.equals(dejaDit.get(i))){
//                            Toast toast = new Toast(MainActivity2.this);
//                            toast.setText("Déjà dit ! !!");
//                            toast.show();
//                            return;
//                        }
//                    }
                    if (q1) {
                        listeAnimation.get(0).start();
                        q2 = true;
                        q1 = false;
                        return;
                    } else if (q2) {
                        listeAnimation.get(1).start();
                        q2 = false;
                        q3 = true;
                        return;
                    } else if (q3) {
                        listeAnimation.get(2).start();
                        animatorSetVictoire.start();
                        q3 = false;
                        q1 = true;

                        enregister.setText("Félicitation");
                        q1 = false;
                        return;
                    }
                }
            }
            for(int i = 0; i < stringVector.size(); i++) {
                if (!valeurReponse.equals(stringVector.get(i))) {
                    Toast toast = new Toast(MainActivity2.this);
                    toast.setText("Mauvaise réponse !!");
                    toast.show();
                    return;
                }
            }
        });
    }
}