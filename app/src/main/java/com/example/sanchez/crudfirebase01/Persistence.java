package com.example.sanchez.crudfirebase01;

import com.google.firebase.database.FirebaseDatabase;

public class Persistence extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
