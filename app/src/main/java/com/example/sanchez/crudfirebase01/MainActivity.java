package com.example.sanchez.crudfirebase01;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sanchez.crudfirebase01.model.Person;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPassword;
    private ListView lvPerson;

    private List<Person> personList = new ArrayList<Person>();
    ArrayAdapter<Person> arrayAdapterPersona;

    Person personaSelected;

    //Objetos necesarios para la conexion
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        lvPerson = findViewById(R.id.lvPerson);
        FirebaseConnection();
        ListPerson();

        //evento apra seleccionar elemento de la listView
        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                personaSelected = (Person) adapterView.getItemAtPosition(i);
                edtName.setText(personaSelected.getName());
                edtLastName.setText(personaSelected.getLastName());
                edtEmail.setText(personaSelected.getEmail());
                edtPassword.setText(personaSelected.getPassword());
            }//onItemClick
        });
    }//onCreate


    private void FirebaseConnection() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        /* -- Manejo de persistencia de forma incorrecta
        firebaseDatabase.setPersistenceEnabled(true);
        */
        databaseReference = firebaseDatabase.getReference();
    }//FirebaseConnection

    private void ListPerson() {
        databaseReference.child("Person").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                personList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Person person = dataSnapshot1.getValue(Person.class);
                    personList.add(person);

                    arrayAdapterPersona = new ArrayAdapter<Person>(MainActivity.this, android.R.layout.simple_list_item_1, personList);
                    lvPerson.setAdapter(arrayAdapterPersona);
                }//for
            }//onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }//onCanceles
        });
    }//listPerson


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }//onCreateOptionsMenu


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name = edtName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add: {
                if(name.equals("") || lastName.equals("") || email.equals("") || password.equals("")){
                    validate();
                }else{
                    Person person = new Person();
                    person.setId(UUID.randomUUID().toString());
                    person.setName(name);
                    person.setLastName(lastName);
                    person.setEmail(email);
                    person.setPassword(password);
                    databaseReference.child("Person").child(person.getId()).setValue(person);
                    Toast.makeText(getApplicationContext(),"Item add",Toast.LENGTH_SHORT).show();
                    cleanEdt();
                }//else
                break;
            }//case
            case  R.id.icon_save: {

                //Actualizar
                Person person = new Person();
                person.setId(personaSelected.getId());
                person.setName(edtName.getText().toString().trim());
                person.setLastName(edtLastName.getText().toString().trim());
                person.setEmail(edtEmail.getText().toString().trim());
                person.setPassword(edtPassword.getText().toString().trim());
                databaseReference.child("Person").child(person.getId()).setValue(person);
                Toast.makeText(getApplicationContext(),"Item update",Toast.LENGTH_SHORT).show();
                cleanEdt();
                break;
            }//case
            case R.id.icon_delete: {
                Toast.makeText(getApplicationContext(),"Item delete",Toast.LENGTH_SHORT).show();
                break;
            }//case
        }//switch
        return super.onOptionsItemSelected(item);
    }


    private void cleanEdt() {
        edtName.setText("");
        edtLastName.setText("");
        edtEmail.setText("");
        edtPassword.setText("");
    }//cleanEdt


    private void validate() {
        String name = edtName.getText().toString();
        String lastName = edtName.getText().toString();
        String email = edtName.getText().toString();
        String password = edtName.getText().toString();

        if(name.equals("")){
            edtName.setError("Required");
        }else if(lastName.equals("")){
            edtLastName.setError("Required");
        }else if(email.equals("")){
            edtEmail.setError("Required");
        }else if(password.equals("")){
            edtPassword.setError("Required");
        }//if
    }//validate

}//MainActivity
