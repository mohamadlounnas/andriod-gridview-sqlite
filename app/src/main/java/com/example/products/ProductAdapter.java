package com.example.products;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter {
    Context context;
    public List<Product> products =new ArrayList<Product>();
    LayoutInflater inflter;
    public ProductAdapter(Context applicationContext, List<Product> products) {
        this.context = applicationContext;
        this.products = products;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return products.size();
    }
    @Override
    public Product getItem(int i) {
        return products.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Product product = products.get(i);
        view = inflter.inflate(R.layout.activity_productview, null);
        TextView productTitle = (TextView) view.findViewById(R.id.productTitle);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        if (product.photo != "") {
            try {
                Bitmap photoBitmap = (Bitmap)Product.StringToBitmap(product.photo);
                image.setImageBitmap(photoBitmap);
            } catch (Exception e) {

            }
        }
        Button delete = (Button) view.findViewById(R.id.deleteProduct);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(context,null );
                dbHandler.deleteProduit(product.id);
                update();
            }
        });
        Button edit = (Button) view.findViewById(R.id.editProduct);
        productTitle.setText(product.label);

        return view;
    }
    public void update() {
        DBHandler dbHandler = new DBHandler(context,null );
        this.products = dbHandler.getProduits();
        notifyDataSetChanged();
    }


}