package com.example.sqlitedatabase1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et_customerName, et_customerAge;
    Button btn_save, btn_showAll;
    Switch sw_active;
    ListView lv;
    ArrayAdapter arrayAdapter;
    DataBaseHelper dataBaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_customerName = findViewById(R.id.customerNameId);
        et_customerAge = findViewById(R.id.customerAgeId);

        btn_save = findViewById(R.id.saveBtnId);
        btn_showAll = findViewById(R.id.showAllBtnId);

        sw_active = findViewById(R.id.switchId);
        lv = findViewById(R.id.lv_customerListId);

        loadData();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel newCustomer;

                try {
                    newCustomer = new CustomerModel(-1, et_customerName.getText().toString(), (Integer.parseInt(et_customerAge.getText().toString())), sw_active.isChecked());
                    Toast.makeText(MainActivity.this, "info save :" + newCustomer, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    newCustomer = new CustomerModel(-1, "error", 0, false);
                    Toast.makeText(MainActivity.this, "Error found: " + e, Toast.LENGTH_SHORT).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success = dataBaseHelper.addOne(newCustomer);

                if (success) {
                    Toast.makeText(MainActivity.this, "True", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "False", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();

            }
        });
    }

    private void loadData() {
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<CustomerModel> everyOne = dataBaseHelper.getEveryOne();

        arrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, R.layout.simple_list_item, everyOne);
        lv.setAdapter(arrayAdapter);
    }
}