package com.example.moblie_labs_19521987;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;
public class SignUpActivity extends AppCompatActivity {
    EditText fullname;
    EditText phone;
    EditText user;
    EditText pass;
    Button signup;

    TextView backtologin;
    private FirebaseAuth mAuth;
     private FirebaseFirestore firestore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        mAuth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        AppCompatActivity activity = (AppCompatActivity) SignUpActivity.this;
        if (activity != null && activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }

        backtologin = findViewById(R.id.tologin);

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fullname = findViewById(R.id.input_username);
        phone = findViewById(R.id.input_phone);
        user = findViewById(R.id.input_user);
        pass = findViewById(R.id.input_pass);

        signup = findViewById(R.id.button_login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidUsername(user.getText().toString()) && pass.getText().toString().length() >= 6){
                    String PassEncrypted = encodeString(pass.getText().toString());

                    String UserName = user.getText().toString();

                    String Phone = phone.getText().toString();

                    String FullName = fullname.getText().toString();


                    Map<String, Object> user = new HashMap<>();
                    user.put("username", UserName);
                    user.put("password", PassEncrypted);
                    user.put("fullname", FullName);
                    user.put("phone", Phone);

                    firestore.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("DEBUG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("DEBUG", "Error adding document", e);
                                }
                            });


                    Toast.makeText(SignUpActivity.this, "Sign up successfully!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{

                    Toast.makeText(SignUpActivity.this, "username contains at least 6 alphanumeric characters, password at least 6 characters !", Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void login(String gmail,String pass){
        mAuth.createUserWithEmailAndPassword(gmail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("DEBUG", "Login sucessfull");
                }
                else {
                    Log.d("DEBUG", "Login fail");
                }
            }
        });
    }

    public static boolean isValidUsername(String username) {

        String regex = "^[a-zA-Z0-9]{6,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static String encodeString(String input) {
        byte[] data = input.getBytes();
        byte[] encodedBytes = Base64.encode(data, Base64.DEFAULT);
        return new String(encodedBytes);
    }
}
