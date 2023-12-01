package jp.ac.hal.iris;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    public static final int RESULT_CODE_LOGIN = 1000;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activitySetup();
    }
    private void activitySetup(){
        EditText email;
        email = findViewById(R.id.email);
        EditText passWord;
        passWord= findViewById(R.id.password);
        Button login;
        login= findViewById(R.id.loginButton);
        TextView createAccount;
        createAccount= findViewById(R.id.createAccount);
        login.setOnClickListener(v -> {
            //入力欄が空欄でないか確認
            if (!email.toString().isEmpty()) {
                //入力欄が空欄でないか確認
                if (!passWord.toString().isEmpty()) {
                    //ログイン処理の開始
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), passWord.getText().toString())
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    Intent intent = getIntent();
                                    setResult(RESULT_CODE_LOGIN, intent);
                                    intent = new Intent(this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "ログインに失敗しました", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    passWord.setError("必須項目です");
                }
            }else {
                email.setError("必須項目です");
            }
        });

        createAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            resultLauncher.launch(intent);
        });
    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == CreateAccountActivity.RESULT_CODE_CREATE_ACCOUNT){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    setResult(RESULT_CODE_LOGIN, intent);
                    finish();
                }
            });
}
