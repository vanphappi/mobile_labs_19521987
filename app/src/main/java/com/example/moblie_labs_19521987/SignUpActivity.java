package com.example.moblie_labs_19521987;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
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
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

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
                    firebaseAuth.createUserWithEmailAndPassword(UserName,PassEncrypted).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String userUid = user.getUid();

                            HashMap<Object, String> users= new HashMap<>();
                            users.put("username", UserName);
                            users.put("password", PassEncrypted);
                            users.put("ID", userUid);
                            users.put("fullname", FullName);
                            users.put("phone", Phone);

                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference ref = db.getReference("user");
                            ref.child(userUid).setValue(users);

                            Toast.makeText(SignUpActivity.this, "Sign up successfully!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }else{
                    Toast.makeText(SignUpActivity.this, "username contains at least 6 alphanumeric characters, password at least 6 characters !", Toast.LENGTH_SHORT).show();
                    return;

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
