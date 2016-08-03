package com.ecoffee.ecoffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.model.Product;

import java.util.List;

/**
 * Created by Vlade Ilievski on 8/3/2016.
 */
public class ProductsSpinnerAdapter extends ArrayAdapter<Product> implements View.OnClickListener {
    private List<Product> items;

    public ProductsSpinnerAdapter(Context context, List<Product> items) {
        super(context, android.R.layout.simple_spinner_item, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        TextView productName = (TextView) convertView.findViewById(android.R.id.text1);

        productName.setText(product.getName());
        return convertView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_dropdown_product_details, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView price = (TextView) convertView.findViewById(R.id.price);

        name.setText(product.getName());
        price.setText("$" + product.getPrice());
        return convertView;
    }

    @Override
    public void onClick(View view) {

    }
}
