package com.ecoffee.ecoffee.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.adapter.TableAdapter;
import com.ecoffee.ecoffee.util.AppUtil;

/**
 * Created by Vlade Ilievski on 7/21/2016.
 */
public class TableActivity extends AppCompatActivity implements View.OnClickListener {

    Button addNewTable;
    ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        addNewTable = (Button) findViewById(R.id.addNewTable);
        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(new TableAdapter(this, R.layout.item_table, AppUtil.getTables()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addNewTable:

                break;

        }
    }
}
