package com.ecoffee.ecoffee.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.model.Product;
import com.ecoffee.ecoffee.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlade Ilievski on 7/22/2016.
 */
public class TableAdapter extends ArrayAdapter<Table> implements View.OnClickListener {
    private List<Table> items;
    private int layoutResource;


    public TableAdapter(Context context, int layoutResource, List<Table> items) {
        super(context, layoutResource, items);
        this.items = items;
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Table table = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
        }

        TextView price = (TextView) view.findViewById(R.id.price);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView orderDescription = (TextView) view.findViewById(R.id.orderDescription);
        Button plusOrder = (Button) view.findViewById(R.id.addOrder);

        price.setText("$" + table.getPrice());

        String tableDesc = getDescription(table);
        orderDescription.setText(!tableDesc.equals("") ? tableDesc : "No orders in this table");
        name.setText(table.getName());
        plusOrder.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addOrder:

                break;
        }
    }

    private String getDescription(Table table) {
        String desc = "";
        for (int i = 0; i < table.getOrder().getProducts().size(); i++) { //Product product:table.getOrder().getProducts()
            if (i == 3) {
                break;
            }
            Product product = table.getOrder().getProducts().get(i);
            desc += product.getName() + "\n";
        }

        return desc;
    }
}