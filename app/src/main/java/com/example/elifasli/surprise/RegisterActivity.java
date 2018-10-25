package com.example.elifasli.surprise;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText e1,e2;
    private FirebaseAuth auth;
    DatabaseReference rootReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);

        auth=FirebaseAuth.getInstance();

        rootReference = FirebaseDatabase.getInstance().getReference();
    }

    public void createUser(View v){

        if(e1.getText().toString().equals("") && e2.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Boş bırakılamaz!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final String email = e1.getText().toString();
            final String password = e2.getText().toString();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                    if (task.isSuccessful()) {

                        firebaseUser=auth.getCurrentUser();
                        User myUserInsertObj = new User(e1.getText().toString(),e2.getText().toString());
                        rootReference.child(firebaseUser.getUid()).setValue(myUserInsertObj)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(getApplicationContext(), "Değişkenler veritabanında depolandı.", Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent myIntent = new Intent(getApplicationContext(),ProfileActivity.class);
                                            startActivity(myIntent);


                                        }
                                    }
                                });

                       /*  Toast.makeText(getApplicationContext(), "Kullanıcı başarılı bir şekilde oluşturuldu.", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(i);*/
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Kullanıcı Oluşturulamadı.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
