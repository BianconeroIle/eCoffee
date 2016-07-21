package com.ecoffee.ecoffee.model;

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
public class itemTable extends AppCompatActivity implements View.OnClickListener {

    TextView numberOfTable;
    TextView price;
    Button plusOrder;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.item_table);

        plusOrder=(Button)findViewById(R.id.plusOrder);
        plusOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.plusOrder:
                break;
        }

    }
}

