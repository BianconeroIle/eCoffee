package com.ecoffee.ecoffee.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.adapter.TableAdapter;
import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.util.AppUtil;

/**
 * Created by Vlade Ilievski on 7/21/2016.
 */
public class TableActivity extends AppCompatActivity implements View.OnClickListener {

    Button addNewTable;
    ListView listView;
    TableAdapter adapter;
    int MIN_NEW_TABLE_TEXT_LENGTH = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        addNewTable = (Button) findViewById(R.id.addNewTable);
        listView = (ListView) findViewById(R.id.listView);

        adapter = new TableAdapter(this, R.layout.item_table, AppUtil.getTables());
        listView.setAdapter(adapter);
        addNewTable.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TableActivity", "onItemClick " + position);
                Intent i = new Intent(TableActivity.this, OrderDetailsActivity.class);
                i.putExtra("clickedTable", position);
                startActivity(i);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                return true;
            }

        });
    }
    private void showAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Do you want to proceed?");
     //   dialogBuilder.setPositiveButton
}


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addNewTable:
                openAddTableDialog();
                break;

        }
    }

    private boolean checkIfTableExist(String newTable) {
        for (Table table : AppUtil.getTables()) {
            if (table.getName().toLowerCase().trim().equals(newTable.toLowerCase().trim())) {
                return true;
            }
        }
        return false;

    }

    private void openAddTableDialog() {
        final EditText inputText = new EditText(this);
        inputText.setHint("Please enter the name of the table");
        inputText.setSingleLine();
        final AlertDialog d = new AlertDialog.Builder(this)
                .setView(inputText)
                .setTitle("Add Table")
                .setPositiveButton("ADD", null) //Set to null. We override the onclick
                .setNegativeButton("CANCEL", null)
                .create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = d.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        inputText.setError(null);
                        if (validateNewTable(inputText)) {
                            d.dismiss();
                        }
                    }
                });

            }
        });
        d.show();


    }

    private boolean validateNewTable(EditText inputText) {
        if (inputText.getText().toString().equals("")) {
            inputText.setError(getString(R.string.enter_table_name));
            return false;
        }

        if (inputText.getText().toString().length() < MIN_NEW_TABLE_TEXT_LENGTH) {
            inputText.setError("Table name should have more than " + MIN_NEW_TABLE_TEXT_LENGTH + " characters");
            return false;
        }

        boolean tableExist = checkIfTableExist(inputText.getText().toString());
        if (tableExist) {
            inputText.setError("Table already exist");
            return false;
        } else {
            AppUtil.addTable(new Table(inputText.getText().toString()));
            adapter.notifyDataSetChanged();
            return true;
        }
    }


    public ListView getListView() {
        return listView;
    }
}

