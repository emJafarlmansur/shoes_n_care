package com.example.cucisepatu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //membuat referensi ke tampilan layout
    Button btn_tambah, btn_tampil;
    EditText edt_nama, edt_usia;
    Switch swt_pelanggan;
    ListView lv_Kust;

    ArrayAdapter kustomrArrayAdp;
    DB_Csepatu dbCsepatu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_tampil = findViewById(R.id.btn_view);
        btn_tambah = findViewById(R.id.btn_add);
        edt_nama = findViewById(R.id.edtx_nm);
        edt_usia = findViewById(R.id.edtx_no);
        swt_pelanggan = findViewById(R.id.sw_1);
        lv_Kust = findViewById(R.id.lv_1);

        dbCsepatu = new DB_Csepatu(MainActivity.this);

        ShowDataToLV();

        //bagian ini memberikan kondisi jika usia sama dengan 0/kosong maka akan muncul pesan error
        btn_tambah.setOnClickListener(new View.OnClickListener() {

            ModelKust kustomModel;

            @Override
            public void onClick(View v) {


                try {
                    kustomModel = new ModelKust(-1, edt_nama.getText().toString(), Integer.parseInt(edt_usia.getText().toString()), swt_pelanggan.isChecked());
//                    Toast.makeText(MainActivity.this, kustomModel.toString(), Toast.LENGTH_SHORT).show();

                } catch (
                        Exception e) {
                    Toast.makeText(MainActivity.this, "Error Menambahkan Data ", Toast.LENGTH_SHORT).show();
                    kustomModel = new ModelKust(-1, "error", 0, false);
                }

                DB_Csepatu dbcSepatu = new DB_Csepatu(MainActivity.this);
                boolean success = dbcSepatu.addOne(kustomModel);
                Toast.makeText(MainActivity.this, "Success " + success, Toast.LENGTH_SHORT).show();
                ShowDataToLV();

            }

        });

        // bagian ini untuk menampilkan data yang tersimpan
        btn_tampil.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DB_Csepatu dbCsepatu = new DB_Csepatu(MainActivity.this);
                ShowDataToLV();
            }
        });
        lv_Kust.setOnItemClickListener(new AdapterView.OnItemClickListener(){
@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
ModelKust clickedKustomr = (ModelKust) parent.getItemAtPosition(position);
dbCsepatu.delOne(clickedKustomr);
ShowDataToLV();
    Toast.makeText(MainActivity.this, "Data Terhapus ", Toast.LENGTH_SHORT).show();

}
        });
     }

    private void ShowDataToLV() {
        kustomrArrayAdp = new ArrayAdapter<ModelKust>(MainActivity.this, android.R.layout.simple_list_item_1, dbCsepatu.getEveryone());
        lv_Kust.setAdapter(kustomrArrayAdp);
    }
}