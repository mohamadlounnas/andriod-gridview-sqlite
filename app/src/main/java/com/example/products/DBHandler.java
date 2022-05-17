package com.example.products;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DBHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE = "ProduitDB.db";
    private static final String TABLE = "Produits";
    public static final String COL_ID = "id";
    public static final String COL_LABEL = "label";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_PHOTO = "photo";
    public DBHandler(Context c, SQLiteDatabase.CursorFactory f) {
        super(c, DATABASE, f, VERSION);
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBHandler.TABLE + " (" +
                    DBHandler.COL_ID + " TEXT PRIMARY KEY," +
                    DBHandler.COL_LABEL + " TEXT," +
                    DBHandler.COL_DESCRIPTION + " TEXT," +
                    DBHandler.COL_PHOTO + " TEXT)"
            ;



    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBHandler.TABLE;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int o, int n) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public List<Product> products = new ArrayList<Product>();


    public void addProduit(Product produit){
        products.add(produit);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHandler.COL_ID, produit.id);
        values.put(DBHandler.COL_LABEL, produit.label);
        values.put(DBHandler.COL_DESCRIPTION, produit.description);
        values.put(DBHandler.COL_PHOTO, produit.photo);
        long newRowId = db.insert(DBHandler.TABLE, null, values);

    }
    public Boolean deleteProduit(String _id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBHandler.TABLE, "id=?", new String[]{_id});
        db.close();
        products.remove(_id);
        return true;
    }
    public ArrayList<Product> getProduits(){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursorProducts = db.rawQuery("SELECT * FROM " + DBHandler.TABLE, null);
            ArrayList<Product> productsArrayList = new ArrayList<Product>();
            if (cursorProducts.moveToFirst()) {
                do {

                    productsArrayList.add(
                        new Product(
                                cursorProducts.getString(0),
                                cursorProducts.getString(1),
                                cursorProducts.getString(2),
                                cursorProducts.getString(3)
                        )
                    );
                } while (cursorProducts.moveToNext());
                // moving our cursor to next.
            }
            // at last closing our cursor
            // and returning our array list.
            cursorProducts.close();

            return productsArrayList;
        }

}