package com.example.moblie_labs_19521987;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText inputFullName;
    private EditText inputGrossSalary;
    private TextView outputInformation;
    private Button buttonCalculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCalculate= findViewById(R.id.buttonCalculate);
        outputInformation = findViewById(R.id.outputInformation);
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
                if(preson.getInformation() != "")
                    outputInformation.setText(outputInformation.getText()+"\n"+preson.getInformation());
                inputFullName.setText(null);
                inputGrossSalary.setText(null);
            }
        });
    }
}