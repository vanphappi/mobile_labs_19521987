package com.example.moblie_labs_19521987;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText inputFullName;
    private EditText inputGrossSalary;
    private ListView outputInformation;
    private Button buttonCalculate;

    private ArrayList<PersonalSalary> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCalculate= findViewById(R.id.buttonCalculate);
        outputInformation = findViewById(R.id.outputInformation);

        arr = new ArrayList<PersonalSalary>();

        ArrayAdapter<PersonalSalary> adapter = new ArrayAdapter<PersonalSalary>(
                MainActivity.this, android.R.layout.simple_list_item_1, arr
        );

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputFullName = findViewById(R.id.inputFullName);
                inputGrossSalary = findViewById(R.id.inputGrossSalary);

                String fullName;
                fullName = inputFullName.getText().toString();
                int grossSalary;
                try {
                    grossSalary = Integer.parseInt(inputGrossSalary.getText().toString());
                }
                catch(Exception e) {
                    grossSalary = 0;
                }
                PersonalSalary preson = new PersonalSalary(fullName,grossSalary);
                if(preson.toString() != "") {
                    arr.add(preson);
                    adapter.notifyDataSetChanged();
                }
                inputFullName.setText(null);
                inputGrossSalary.setText(null);
                
            }
        });
        outputInformation.setAdapter(adapter);
    }
}