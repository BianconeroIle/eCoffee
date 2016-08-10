package com.ecoffee.ecoffee.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.util.AppPreferences;
import com.ecoffee.ecoffee.util.AppUtil;

import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username;
    EditText password;
    Button loginButton;
    CheckBox rememberMeCheckBox;
    AppPreferences preferences;
    TextView expiredToken;
    boolean loginExpired = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new AppPreferences(getApplicationContext());
        setContentView(R.layout.activity_main);

        AppPreferences preferences = new AppPreferences(this);
        List<Table> savedTables = preferences.getSavedTables();
        if (!savedTables.isEmpty()) {
            AppUtil.addSavedTables(savedTables);
        } else {
            AppUtil.generateData();
        }


        if (preferences.isAuthorised()) {
            openTableActivity();
        }

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);
        expiredToken = (TextView) findViewById(R.id.expiredToken);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("loginExpired")) {
            loginExpired = getIntent().getExtras().getBoolean("loginExpired");
            if (loginExpired) {
                expiredToken.setVisibility(View.VISIBLE);


            }
        }

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (loginExpired) {
                    expiredToken.setVisibility(View.GONE);
                }
            }
        });

    }


    public void validateCredentials() {
        username.setError(null);
        password.setError(null);

        String u = username.getText().toString();
        String p = password.getText().toString();

        if (u.equals("")) {
            username.setError("This field is required!");

        }

        if (p.equals("")) {
            password.setError("This field is required!");
        }

        if (AppUtil.checkUserExist(u, p)) {
            if (rememberMeCheckBox.isChecked()) {
                preferences.setUserLogged(true);
                preferences.setExpirationDate(new Date());
                preferences.setUserName(u);
            }
            openTableActivity();
        } else {
            Toast.makeText(this, "The username or password you have entered is invalid.", Toast.LENGTH_LONG).show();
        }
    }

    private void openTableActivity() {
        Intent intent = new Intent(LoginActivity.this, TableActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                validateCredentials();
                break;
        }
    }


}
