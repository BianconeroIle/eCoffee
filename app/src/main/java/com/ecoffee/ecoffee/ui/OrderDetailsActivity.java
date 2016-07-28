package com.ecoffee.ecoffee.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.adapter.DescriptionAdapter;
import com.ecoffee.ecoffee.intefrace.OnOrderDataChanged;
import com.ecoffee.ecoffee.model.Product;
import com.ecoffee.ecoffee.util.AppUtil;

import java.util.List;

/**
 * Created by Vlade Ilievski on 7/26/2016.
 */


public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnOrderDataChanged {
    Button add_new_order;
    ListView orderDescriptionListView;
    DescriptionAdapter dAdapter;
    TextView infoText;
    int clickedTable = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("clickedTable")) {
            clickedTable = getIntent().getExtras().getInt("clickedTable");
        }
        initView();

        List<Product> products = AppUtil.getTables().get(clickedTable).getOrder().getProducts();

        if (products == null || products.isEmpty()) {
            infoText.setText("No orders in this table");
            orderDescriptionListView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }

        dAdapter = new DescriptionAdapter(this, AppUtil.getProducts(),this);
        orderDescriptionListView.setAdapter(dAdapter);

    }
    @Override
    public void OnOrderDataChanged(int count) {
        if (count != 0) {
            orderDescriptionListView.setVisibility(View.VISIBLE);
            infoText.setVisibility(View.GONE);
        } else {
            infoText.setText("No orders");
            orderDescriptionListView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        infoText = (TextView) findViewById(R.id.intoText);
        add_new_order = (Button) findViewById(R.id.add_new_order);
        orderDescriptionListView = (ListView) findViewById(R.id.orderDescriptionListView);
        add_new_order.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_order:
                MakeOrderCustomDialog dialog = new MakeOrderCustomDialog(this);
                dialog.show();
                break;
        }

    }



}
