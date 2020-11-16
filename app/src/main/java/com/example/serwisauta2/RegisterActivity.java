package com.example.serwisauta2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.jar.Attributes;

public class RegisterActivity extends AppCompatActivity {

    private EditText UserName, UserEmail, UserPassword, UserConfirmPassword;
    private Button CreateAccButton;
    private TextView AlreadyHaveAcc;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    private static final String TAG = "RegisterActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        AlreadyHaveAcc = (TextView) findViewById(R.id.login_acc_link);
        UserEmail = (EditText) findViewById(R.id.registerEmail);
        UserPassword = (EditText) findViewById(R.id.registerPassword);
        UserName = (EditText) findViewById(R.id.registerName);
        UserConfirmPassword = (EditText) findViewById(R.id.registerConfirmPassword);
        CreateAccButton = (Button) findViewById(R.id.buttonRegister);
        loadingBar = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();

        AlreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToLoginActivity();
            }
        });



        CreateAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = UserEmail.getText().toString();
                String password = UserPassword.getText().toString();
                String confirmpassword = UserConfirmPassword.getText().toString();
                String username = UserName.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Proszę wprowadzić email...",Toast.LENGTH_SHORT).show();

                }
                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"Proszę wprowadzić hasło...",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(confirmpassword))
                {
                    Toast.makeText(getApplicationContext(), "Proszę potwierdzić hasło...",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(confirmpassword))
                {
                    Toast.makeText(getApplicationContext(),"Hasła nie zgadzają się...",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(username))
                {
                    Toast.makeText(getApplicationContext(),"Proszę wprowadzić nazwę użytkownika...",Toast.LENGTH_SHORT).show();
                }
                else {


                    user = new User(email, password, username);
                    registerUser(email, password);
                    loadingBar.setTitle("Rejestracja konta");
                    loadingBar.setMessage("Proszę czekać, trwa rejestracja twojego konta...");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);
                }
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
            SendUserToMainActivity();
        }
    }

    public void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Pomyślnie zarejestrowano konto. Zweryfikuj swój email.", Toast.LENGTH_SHORT).show();
                                        userData();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        mAuth.signOut();

                                    }else{
                                        Toast.makeText(getApplicationContext(), "Rejestracja nie przebiegła pomyślnie, spróbuj ponownie", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            loadingBar.dismiss();

                        } else
                            {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }
    public void userData() {
        String username = UserName.getText().toString();
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(mAuth.getUid());
        user = new User(username, email, password);
        databaseReference.setValue(user);
    }
    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);

    }
    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
