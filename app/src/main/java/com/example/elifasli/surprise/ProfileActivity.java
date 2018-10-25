package com.example.elifasli.surprise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    TextView profileText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth=FirebaseAuth.getInstance();
        profileText = (TextView)findViewById(R.id.textView);
        //O anda giriş yapan kullanıcıyı getirir.
        user=auth.getCurrentUser();

        //çıkış yapmak için buraya yazabiliriz ama tıklama olayında istediğimiz için metoda yazacaz
        //auth.signOut();

        profileText.setText(user.getEmail());


    }

        public void signOut(View v)
        {
            auth.signOut();
            finish();
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
}
