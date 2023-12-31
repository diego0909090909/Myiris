package jp.ac.hal.iris;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {
    public static final int RESULT_CODE_CREATE_ACCOUNT = 2000;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        activitySetup();
    }

    private void activitySetup(){
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText rePassword = findViewById(R.id.rePassword);
        Button create = findViewById(R.id.createAccount);
        mAuth = FirebaseAuth.getInstance();
        create.setOnClickListener(v -> {
            if (password.getText().toString().equals(rePassword.getText().toString())){

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                Toast.makeText(CreateAccountActivity.this, "アカウントを作成しました！",Toast.LENGTH_LONG).show();
                                setResult(RESULT_CODE_CREATE_ACCOUNT, getIntent());
                                Intent intent = new Intent(this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(CreateAccountActivity.this, "アカウント作成に失敗しました",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
