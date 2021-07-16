package com.master.passwordstore2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton switchButton;
    DBHelper dbHelper;
    Toolbar toolbar;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //implementing methods to get menu in toolbar

            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            dbHelper = new DBHelper(this);
            recyclerView = findViewById(R.id.recycleview);
            //implements the binding of recyclerview with customAdapter
            getRecyclerView();


            switchButton = findViewById(R.id.addbutton);
            switchActivity(switchButton);

    }


    public  void getRecyclerView(){

        dbHelper.getData();

        List<String> app = dbHelper.getAppName();
        List<String> pass = dbHelper.getPasswords();

        //Sets Linear layout manager which gives linear order of recycler views
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Adapter binds the dataset to to each view
        adapter = new CustomAdapter(getApplicationContext(),app, pass);
        recyclerView.setAdapter(adapter);

    }

    public  void switchActivity(View switchButton){

        switchButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,AddPassword.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            MenuInflater menuInflater = getMenuInflater();

            menuInflater.inflate(R.menu.appbarmenu,menu);

            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

            MenuItem item = menu.findItem(R.id.search);

            SearchView searchView = (SearchView)item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }


            });

            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception in Creating Menu Inflater");
            return false;
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            if (item.getItemId() == R.id.deleteAll) {
                dbHelper.deleteAllRows();
                recreate();
            }

            return true;

    }
}