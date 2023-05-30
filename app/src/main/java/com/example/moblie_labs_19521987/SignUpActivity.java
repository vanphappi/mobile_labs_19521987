package com.example.moblie_labs_19521987;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SignUpActivity extends AppCompatActivity {
    EditText fullname;
    EditText phone;
    EditText user;
    EditText pass;
    Button signup;
    FirebaseDatabase db;
    DatabaseReference myRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        fullname = findViewById(R.id.input_username);
        phone = findViewById(R.id.input_phone);
        user = findViewById(R.id.input_user);
        pass = findViewById(R.id.input_pass);

        signup = findViewById(R.id.button_login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidUsername(user.getText().toString()) && pass.getText().toString().length() >= 6){

                    String PassEncrypted = encryptPassword(pass.getText().toString());

                    String UserName = user.getText().toString();

                    String Phone = phone.getText().toString();

                    String FullName = fullname.getText().toString();

                    db = FirebaseDatabase.getInstance();

                    Map<String, Object> userData = new HashMap<>();
                    userData.put("FullName", FullName);
                    userData.put("Phone", Phone);
                    userData.put("UserName", UserName);
                    userData.put("PassWord", PassEncrypted);

                    db.collection("users")
                            .document("user1")
                            .set(userData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Data saved successfully
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle the failure
                                }
                            });

                }else{
                    System.out.println("Not Valid");

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

    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] byteData = md.digest();

            // Convert the byte array to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
