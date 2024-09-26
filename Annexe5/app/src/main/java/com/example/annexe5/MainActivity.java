package com.example.annexe5;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        Hashtable<String, Object> temp = new Hashtable<>();
        temp.put("position", "3");
        temp.put("nom", "Touch Me");
        temp.put("date", "22/03/1986");
        temp.put("image", R.drawable.touchme);
        vector.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "1");
        temp.put("nom", "Snitch & Rats");
        temp.put("date", "2/10/2020");
        temp.put("image", R.drawable.savage);
        vector.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "8");
        temp.put("nom", "Nothing's gonna stop me now");
        temp.put("date", "30/05/1986");
        temp.put("image", R.drawable.nothing);
        vector.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "31");
        temp.put("nom", "SantaMaria");
        temp.put("date", "28/05/1986");
        temp.put("image", R.drawable.santamaria);
        vector.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "108");
        temp.put("nom", "Hotboy");
        temp.put("date", "10/04/2018");
        temp.put("image", R.drawable.hotboy);
        vector.add(temp);



        SimpleAdapter adapter = new SimpleAdapter(this, vector, R.layout.pochette, new String[]{"position", "nom", "date", "image"}, new int[]{R.id.textPosition, R.id.textNom, R.id.textDate, R.id.image});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast toast = new Toast(this);
            toast.setText("Vous avez cliqué sur la musique " + vector.get(position).get("nom"));
            toast.show();
        });
    }
}