package com.ecoffee.ecoffee.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.adapter.ProductsSpinnerAdapter;
import com.ecoffee.ecoffee.model.Product;
import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.util.AppPreferences;
import com.ecoffee.ecoffee.util.AppUtil;

/**
 * Created by Vlade Ilievski on 7/28/2016.
 */
public class MakeOrderCustomDialog extends Dialog implements View.OnClickListener {

    Button buttonNO;
    Button buttonYES;

    Spinner quantitySpinner;
    Spinner productsSpinner;
    Context context;
    TextView produtsTextView;
    TextView quantityTextView;
    int tablePosition = 0;
    Table table;
    OnOrderAdded listener;
    AppPreferences preferences;

    public interface OnOrderAdded {
        void onOrderAdded();
    }

    public MakeOrderCustomDialog(Context context, int tablePosition, OnOrderAdded listener) {
        super(context);
        this.tablePosition = tablePosition;
        this.listener = listener;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.make_orders);
        preferences = new AppPreferences(getContext());
        table = AppUtil.getTables().get(tablePosition);


        buttonYES = (Button) findViewById(R.id.buttonYES);
        buttonNO = (Button) findViewById(R.id.buttonNO);
        produtsTextView = (TextView) findViewById(R.id.productsTextView);
        quantityTextView = (TextView) findViewById(R.id.quantityTextView);
        buttonYES.setOnClickListener(this);
        buttonNO.setOnClickListener(this);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        quantitySpinner = (Spinner) findViewById(R.id.quantitySpinner);
        productsSpinner = (Spinner) findViewById(R.id.productsSpinner);

        ProductsSpinnerAdapter productsSpinnerAdapter = new ProductsSpinnerAdapter(getContext(), AppUtil.getProducts());
        productsSpinner.setAdapter(productsSpinnerAdapter);

    }

    @Override
    public void onClick(View view) {
        if (!preferences.checkIsAuthenticatedAndLogout()) {
            return;
        }
        switch (view.getId()) {
            case R.id.buttonYES:
                addOrder();
                break;
            case R.id.buttonNO:
                dismiss();
                break;
        }
    }


    private void addOrder() {
        int selectedProductPosition = productsSpinner.getSelectedItemPosition();
        int selectedQuantityPosition = quantitySpinner.getSelectedItemPosition();

        Product selectedProduct = AppUtil.getProducts().get(selectedProductPosition);
        //int quantity = Integer.valueOf(getContext().getResources().getStringArray(R.array.quantity_numbers)[selectedQuantityPosition]);
        int quantity = getContext().getResources().getIntArray(R.array.quantity_integers)[selectedQuantityPosition];


       /* for (int i = 0; i < quantity; i++) {
            table.getOrder().addOrder(selectedProduct);
        }*/
        AppUtil.addOrderOnTable(preferences, table, selectedProduct, quantity);


        Log.d("MakeOrderCustomDialog", "order=" + table.getOrder());

        if (listener != null) {
            listener.onOrderAdded();
        }
        dismiss();
    }
}


