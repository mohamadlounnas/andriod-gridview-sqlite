package com.example.products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ProductAdapter productAdapter;
    FloatingActionButton addProductButton;
    List<Product> products = new ArrayList<Product>();
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHandler = new DBHandler(MainActivity.this,null );
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        gridView=findViewById(R.id.grid);
        products = dbHandler.getProduits();
        productAdapter = new ProductAdapter(MainActivity.this, products);
        gridView.setAdapter(productAdapter);

        addProductButton = findViewById(R.id.addProductButton);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dbHandler.deleteProduit(productAdapter.products.get(0).id);
                productAdapter.update();
                gridView.setAdapter(productAdapter);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem getItem = menu.findItem(R.id.add_product_view);
        if (getItem != null) {
            AppCompatButton button = (AppCompatButton) getItem.getActionView();
            //button.setOnClickListener(new View.OnClickListener() {public void onClick(View v) {
            //    Intent i = new Intent(MainActivity.this, AddProductActivity.class);
            //    startActivity(i);
            //}});
        }

        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(MainActivity.this, AddProductActivity.class);
        startActivity(i);
        productAdapter.update();                gridView.setAdapter(productAdapter);

        return true;
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        productAdapter.update();
        gridView.setAdapter(productAdapter);

    }
    @Override
    public void onResume() {
        productAdapter.update();
        gridView.setAdapter(productAdapter);
        super.onResume();
    }

}