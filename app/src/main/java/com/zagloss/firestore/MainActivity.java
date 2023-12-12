package com.zagloss.firestore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email, passw, name;
    Button guardar, borrar, recuperar;
    TextView recuEmail, recuNombre;

    //Instancia de firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        passw = findViewById(R.id.password);
        name = findViewById(R.id.name);

        guardar = findViewById(R.id.guardar);
        borrar = findViewById(R.id.borrar);
        recuperar = findViewById(R.id.recuperar);

        recuEmail = findViewById(R.id.textViewRecuEmail);
        recuNombre = findViewById(R.id.textViewRecuNombre);

        //Presionar el boton de guardar
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creamos el HasMap con la informacion del usuario
                HashMap<String, String> userInfo = new HashMap<String, String>();
                userInfo.put("email", email.getText().toString());
                userInfo.put("name", name.getText().toString());
                userInfo.put("password", passw.getText().toString());

                //Creamos la colección users, le metemos un documento que será el email, y le pasamos el hashMap con la info
               db.collection("Users").document(email.getText().toString()).set(userInfo);
            }
        });

    }


}