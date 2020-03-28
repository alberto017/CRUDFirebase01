package com.example.sanchez.crudfirebase01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.icon_add: {
                Toast.makeText(getApplicationContext(),"Item add",Toast.LENGTH_SHORT).show();
                break;
            }
            case  R.id.icon_save: {
                Toast.makeText(getApplicationContext(),"Item save",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.icon_delete: {
                Toast.makeText(getApplicationContext(),"Item delete",Toast.LENGTH_SHORT).show();
                break;
            }
        }//switch
        return super.onOptionsItemSelected(item);
    }
}
