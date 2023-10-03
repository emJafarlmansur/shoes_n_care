package com.example.cucisepatu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Display;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB_Csepatu extends SQLiteOpenHelper {

    public static final String TABLE_KUSTOMER = "TABLE_KUSTOMER";
    public static final String KUSTOMER_NAME = "KUSTOMER_NAME";
    public static final String KUSTOMER_AGE = "KUSTOMER_AGE";
    public static final String ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    public static final String KOLOM_ID = "KOLOM_ID";

    public DB_Csepatu(@Nullable Context context) {
        super(context, "kustomr.db", null, 1);
    }

    //perintah ini untuk memanggil ketika DB akan diakses, disini juga termasuk membuat DB baru
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery= "CREATE TABLE " + TABLE_KUSTOMER + " (" + KOLOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KUSTOMER_NAME + " TEXT, " + KUSTOMER_AGE + " INT, " + ACTIVE_CUSTOMER + " BOOL)";
        db.execSQL(createTableQuery);

    }
    //perintah ini dipanggil jika DB ada perubahan, semisal jika aplikasi ada pembaruan fitur
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(ModelKust kustomModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv =new ContentValues();

        cv.put(KUSTOMER_NAME,kustomModel.getNama());
        cv.put(KUSTOMER_AGE,kustomModel.getUsia());
        cv.put(ACTIVE_CUSTOMER,kustomModel.isActive());

        long insert = db.insert(TABLE_KUSTOMER,null,cv);
        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }
public boolean delOne(ModelKust modelKust){
        SQLiteDatabase db = getWritableDatabase();
        String queString = " DELETE FROM " + TABLE_KUSTOMER + " WHERE " + KOLOM_ID + " = " + modelKust.getId();
    Cursor cursor = db.rawQuery(queString, null);
    if(cursor.moveToFirst()){
        return true;
    }else{
        return false;
    }

}
    public boolean updateOne(ModelKust modelKust) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KUSTOMER_NAME, modelKust.getNama());
        values.put(KUSTOMER_AGE, modelKust.getUsia());
        values.put(ACTIVE_CUSTOMER, modelKust.isActive() ? 1 : 0);

        String whereClause = KOLOM_ID + " = ?";
        String[] whereArgs = {String.valueOf(modelKust.getId())};

        return db.update(TABLE_KUSTOMER, values, whereClause, whereArgs) > 0;
    }

    public List<ModelKust> getEveryone(){
        List<ModelKust> returnList = new ArrayList<>();

        //mengambil data dari DB

        String queString= " SELECT * FROM "+ TABLE_KUSTOMER;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queString,null);

        //perulangan hasil dari setiap data DB dan membuat objek baru yang akan diulang

        if(cursor.moveToFirst()){
            do{
                int kustomrID= cursor.getInt(0);
                String kustomrNM=cursor.getString(1);
                int kustomrAge=cursor.getInt(2);
                boolean kustomrActive=cursor.getInt(3) == 1 ? true : false;

                ModelKust newMokust =new ModelKust(kustomrID,kustomrNM,kustomrAge,kustomrActive);
                returnList.add(newMokust);
            }
            while (cursor.moveToNext());
        }
        else{
            //jika gagal menampilkan data , data tidak ditampilkan
        }
        cursor.close();
        db.close();
       return returnList;
    }
    public ModelKust getOne(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KUSTOMER,
                new String[]{KOLOM_ID, KUSTOMER_NAME, KUSTOMER_AGE, ACTIVE_CUSTOMER},
                KOLOM_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            int kustomrID = cursor.getInt(0);
            String kustomrNM = cursor.getString(1);
            int kustomrAge = cursor.getInt(2);
            boolean kustomrActive = cursor.getInt(3) == 1;

            ModelKust modelKust = new ModelKust(kustomrID, kustomrNM, kustomrAge, kustomrActive);
            cursor.close();
            return modelKust;
        } else {
            return null;
        }
    }

}
