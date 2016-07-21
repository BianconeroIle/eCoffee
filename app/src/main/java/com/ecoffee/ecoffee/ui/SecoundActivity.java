package com.ecoffee.ecoffee.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;

/**
 * Created by Vlade Ilievski on 7/21/2016.
 */
public class SecoundActivity extends AppCompatActivity implements View.OnClickListener {

    Button addNewTable;



    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_tables);

        addNewTable=(Button)findViewById(R.id.addNewTable);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addNewTable:

                break;

        }
    }
}
