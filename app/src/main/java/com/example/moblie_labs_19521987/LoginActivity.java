package com.example.moblie_labs_19521987;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import android.util.Base64;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    TextView signup;
    Button login;
    FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        firestore = FirebaseFirestore.getInstance();

        AppCompatActivity activity = (AppCompatActivity) LoginActivity.this;
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }

        signup = findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });


        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_password);

        login = findViewById(R.id.button_login);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserName = username.getText().toString();
                String PassWord = encodeString(password.getText().toString());

                if(UserName.isEmpty() || PassWord.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter your username or password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    firestore.collection("users")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult() != null) {
                                            List<users> usersList = task.getResult().toObjects(users.class);
                                            for (users user : usersList) {
                                                if(user.getUsername().toString() == UserName && user.getPassword().toString() == PassWord){
                                                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(),HomePageActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                            Toast.makeText(LoginActivity.this, "Account does not exist!", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                    } else {
                                        Log.w("DEBUG", "Error getting documents.", task.getException());
                                    }
                                }
                            });
                }

            }
        });
    }

    public static String encodeString(String input) {
        byte[] data = input.getBytes();
        byte[] encodedBytes = Base64.encode(data, Base64.DEFAULT);
        return new String(encodedBytes);
    }
}