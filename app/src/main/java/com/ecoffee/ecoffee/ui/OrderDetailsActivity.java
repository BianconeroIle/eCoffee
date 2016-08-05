package com.ecoffee.ecoffee.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.adapter.DescriptionAdapter;
import com.ecoffee.ecoffee.intefrace.OnOrderDataChanged;
import com.ecoffee.ecoffee.model.Order;
import com.ecoffee.ecoffee.model.Product;
import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.util.AppUtil;

import java.util.List;

/**
 * Created by Vlade Ilievski on 7/26/2016.
 */


public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnOrderDataChanged, MakeOrderCustomDialog.OnOrderAdded {
    Button add_new_order;
    Button renameTable;
    TextView totalPrice;
    ListView orderDescriptionListView;
    DescriptionAdapter dAdapter;
    TextView infoText;
    TextView tableName;
    TextView newText;
    int clickedTable = 1;
    Table table;
    int MIN_NEW_TABLE_TEXT_LENGTH = 5;
    View totalLayout;
    Activity activity;
    Button resetBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        activity = this;

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("clickedTable")) {
            clickedTable = getIntent().getExtras().getInt("clickedTable");
            //table= (Table) getIntent().getExtras().getSerializable("tableObject"); // !!!!!!!!!!!!!!!!   PRIMER   !!!!!!!!!!
        }
        initView();
        table = AppUtil.getTables().get(clickedTable);
        tableName.setText(table.getName());
        totalPrice.setText("$" + table.getPrice());
        if (table.getOrder().isPaid()) {
            disableTableFunctionality();
        }

        //List<Product> products = AppUtil.getTables().get(clickedTable).getOrder().getProducts();
        List<Product> products = table.getOrder().getProducts();

        if (products == null || products.isEmpty()) {
            infoText.setText("No orders in this table");
            orderDescriptionListView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }


        dAdapter = new DescriptionAdapter(this, products, this);
        orderDescriptionListView.setAdapter(dAdapter);

        setLongClickListener();
    }


    private void setLongClickListener() {
        orderDescriptionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                showAlertDialog(table, position);
                return false;
            }
        });
    }

    private void showAlertDialog(final Table table, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete item");
        builder.setMessage("Do you want to delete this item?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                table.getOrder().deleteProducts(table.getOrder().getProducts().get(position));
                dAdapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public void OnOrderDataChanged(int count) {
        if (count != 0) {
            orderDescriptionListView.setVisibility(View.VISIBLE);
            infoText.setVisibility(View.GONE);
            totalPrice.setText("$" + table.getPrice());
            totalPrice.setVisibility(View.VISIBLE);
        } else {
            infoText.setText("No orders");
            orderDescriptionListView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
            totalPrice.setVisibility(View.GONE);
        }
    }


    private void initView() {
        infoText = (TextView) findViewById(R.id.intoText);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        tableName = (TextView) findViewById(R.id.tableName);
        renameTable = (Button) findViewById(R.id.renameTable);
        add_new_order = (Button) findViewById(R.id.add_new_order);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        orderDescriptionListView = (ListView) findViewById(R.id.orderDescriptionListView);
        totalLayout = (View) findViewById(R.id.totalLayout);
        add_new_order.setOnClickListener(this);
        renameTable.setOnClickListener(this);
        totalLayout.setOnClickListener(this);
        resetBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_order:
                MakeOrderCustomDialog dialog = new MakeOrderCustomDialog(this, clickedTable, this);
                dialog.show();
                break;
            case R.id.renameTable:
                openRenameTableDialog();
                break;
            case R.id.totalLayout:
                openTotalDialog();
                break;
            case R.id.resetBtn:
                openResetDialog();
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

    private void openRenameTableDialog() {
        final EditText inputText = new EditText(this);
        inputText.setText(table.getName());
        inputText.setHint("Enter new table name");
        inputText.setSingleLine();
        final AlertDialog d = new AlertDialog.Builder(this)
                .setView(inputText)
                .setTitle("Rename table")
                .setPositiveButton("Rename", null) //Set to null. We override the onclick
                .setNegativeButton("Cancel", null)
                .create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = d.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        inputText.setError(null);
                        if (renameTable(inputText)) {
                            d.dismiss();
                        }
                    }
                });

            }
        });
        d.show();
    }

    private void openTotalDialog() {


        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Pay table")
                .setMessage("Do you want to make a payment?")
                .setPositiveButton("Pay", null)
                .setNegativeButton("Cancel", null)
                .create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {
                final Button positiveBtn = d.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resetBtn.setVisibility(View.VISIBLE);
                        table.getOrder().setPaid(true);
                        disableTableFunctionality();
                        d.dismiss();
                    }
                });
            }

        });
        d.show();
    }

    private void openResetDialog() {
        final AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Refresh table")
                .setMessage("Do you want to refresh this table?")
                .setPositiveButton("Refresh", null)
                .setNegativeButton("Cancel", null)
                .create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {
                final Button positiveBtn = d.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        table.getOrder().deleteOrder();
                        table.getOrder().setPaid(false);
                        enableTableFunctionality();
                        d.dismiss();
                    }
                });
            }
        });
        d.show();
    }

    private boolean renameTable(EditText inputText) {
        if (inputText.getText().toString().equals("")) {
            inputText.setError(getString(R.string.rename_table_name));
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
            table.setName(inputText.getText().toString());
            tableName.setText(inputText.getText().toString());
            return true;
        }
    }

    public void deleteOrder() {
        dAdapter.notifyDataSetChanged();
    }


    @Override
    public void onOrderAdded() {
        dAdapter.notifyDataSetChanged();
        Log.d("OrderDetailsActivity", AppUtil.getTables().get(clickedTable).getOrder().getProducts().toString());
    }

    private void enableTableFunctionality() {
        resetBtn.setVisibility(View.GONE);
        activity.findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        renameTable.setEnabled(true);
        add_new_order.setEnabled(true);
        setLongClickListener();
        totalLayout.setOnClickListener(this);
    }

    private void disableTableFunctionality() {
        resetBtn.setVisibility(View.VISIBLE);
        activity.findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.paidGreen));
        renameTable.setEnabled(false);
        add_new_order.setEnabled(false);
        orderDescriptionListView.setOnItemLongClickListener(null);
        totalLayout.setOnClickListener(null);
    }

}


