package com.zagloss.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email, passw, name;
    Button guardar, borrar, recuperar;
    TextView recuEmail, recuNombre;

    ConstraintLayout main;
    ArrayList<String> idUsed;

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

        main = findViewById(R.id.constrainLayoutID);

        idUsed = new ArrayList<String>();


        //Guardar todos los id para comprobar si se puede registrar
        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documents : task.getResult()) {
                        idUsed.add(documents.getId());
                    }
                }
            }
        });








        //Presionar el boton de guardar
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creamos el HasMap con la informacion del usuario
                HashMap<String, String> userInfo = new HashMap<String, String>();
                userInfo.put("name", name.getText().toString());
                userInfo.put("password", passw.getText().toString());

                //Creamos la colección users, le metemos un documento que será el email, y le pasamos el hashMap con la info
               db.collection("Users").document(email.getText().toString()).set(userInfo);
               //Este metodo sirve tanto para Crear, como para Updatear
            }
        });

        //Presionar el boton de borrar
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hacemos referencia a nuestra instancia con db y seleccionamos la coleccion de users, y el
                //documento de nuestro usuario, es decir, su email
                db.collection("Users").document(email.getText().toString()).delete();
                //Añadimos el .delete() y po atomar por culo
            }
        });

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Esta parafernalia no tiene mucha explicacion pq hay que hacerla asi y a joderse, lo unico saber que "name" y "password"
                //tienen que estar igual escritos que al declararlos en el hasmap, ya que son las claves del documento
                db.collection("Users").document(email.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        recuEmail.setText(documentSnapshot.get("name").toString());
                        recuNombre.setText(documentSnapshot.get("password").toString());
                    }
                });
            }
        });


        //Chekear si ya existe un documento con el mismo nombre, en nuestro caso, mismo email
        // https://stackoverflow.com/questions/53332471/checking-if-a-document-exists-in-a-firestore-collection

        //Conseguir todos los documentos dentro de una coleccion
        // https://stackoverflow.com/questions/55913722/how-do-query-all-documents-of-a-collection-in-fire-store



    }


}