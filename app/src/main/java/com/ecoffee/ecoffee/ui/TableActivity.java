package com.ecoffee.ecoffee.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.adapter.TableAdapter;
import com.ecoffee.ecoffee.intefrace.OnRefreshTable;
import com.ecoffee.ecoffee.intefrace.OnTableDataChanged;
import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.util.AppPreferences;
import com.ecoffee.ecoffee.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilija Angeleski on 7/21/2016.
 */
public class TableActivity extends AppCompatActivity implements View.OnClickListener, OnTableDataChanged, MakeOrderCustomDialog.OnOrderAdded, OnRefreshTable {

    Button addNewTable;
    ListView listView;
    TableAdapter adapter;
    TextView infoText;
    AppPreferences preferences;

    int MIN_NEW_TABLE_TEXT_LENGTH = 5;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        preferences = new AppPreferences(this);
        addNewTable = (Button) findViewById(R.id.addNewTable);
        listView = (ListView) findViewById(R.id.listView);
        infoText = (TextView) findViewById(R.id.infoText);


        adapter = new TableAdapter(this, R.layout.item_table, AppUtil.getTables(), this, this, this);
        listView.setAdapter(adapter);
        addNewTable.setOnClickListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TableActivity", "onItemClick " + position);
                Intent i = new Intent(TableActivity.this, OrderDetailsActivity.class);
                i.putExtra("clickedTable", position);
                i.putExtra("tableObject", AppUtil.getTables().get(position));
                startActivity(i);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Table table = AppUtil.getTables().get(position);
                showAlertDialog(table);
                return true;
            }


        });
        List<Table> tables = AppUtil.getTables();
        if (tables == null || tables.isEmpty()) {
            infoText.setText("No tables");
            listView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }


    }

    private void showAlertDialog(final Table table) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Delete table?");
        dialogBuilder.setMessage("Do you want to delete: " + table.getName() + " ?");
        dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AppUtil.deleteTable(table, preferences);
                adapter.notifyDataSetChanged();

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogBuilder.show();
    }


    @Override
    public void onClick(View view) {
        if (!preferences.checkIsAuthenticatedAndLogout()) {
            return;
        }
        switch (view.getId()) {
            case R.id.addNewTable:
                openAddTableDialog();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.search, menu);
        MenuInflater inflater = getMenuInflater();

        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchTables(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchTables(String query) {
        Log.d("TableActivity","searchTables="+query);
        List<Table> filteredTables = new ArrayList<>();
        for (Table table : AppUtil.getTables()) {
            if (table.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredTables.add(table);
            }
        }

        if(!filteredTables.isEmpty()){
            adapter.clear();
            adapter.addAll(filteredTables);
            adapter.notifyDataSetChanged();
        }else{
            adapter.clear();
            adapter.addAll(AppUtil.getTables());
            adapter.notifyDataSetChanged();
        }
    }

    public void showLogoutDialog() {
        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this);
        logoutDialog.setTitle("Logout");
        logoutDialog.setMessage("Do you want to menu?");
        logoutDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                preferences.clearData();
                Intent intent = new Intent(TableActivity.this, LoginActivity.class);
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        logoutDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        logoutDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                showLogoutDialog();
                break;
        }
        return super.onOptionsItemSelected(item);

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

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
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
            AppUtil.addTable(new Table(inputText.getText().toString()), preferences);
            adapter.notifyDataSetChanged();
            return true;
        }
    }


    public ListView getListView() {
        return listView;
    }

    @Override
    public void onTableDataChanged(int count) {
        Log.d("TableActivity", "ontabledatachanged count" + count);
        if (count != 0) {
            listView.setVisibility(View.VISIBLE);
            infoText.setVisibility(View.GONE);
        } else {
            infoText.setText("No Tables");
            listView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onOrderAdded() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshTable(boolean refresh) {
        Log.d("onRefreshTable", "onRefreshTable " + refresh);
        if (refresh) {
            adapter.notifyDataSetChanged();
        }
    }


}



