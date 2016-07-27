package com.ecoffee.ecoffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.intefrace.OnOrderDataChanged;
import com.ecoffee.ecoffee.model.Product;

import java.util.List;

/**
 * Created by Vlade Ilievski on 7/25/2016.
 */
public class DescriptionAdapter extends ArrayAdapter<Product> implements View.OnClickListener {

    private List<Product> items;
    OnOrderDataChanged listener;


    public DescriptionAdapter(Context context, List<Product> products , OnOrderDataChanged listener) {
        super(context, R.layout.item_description,products);
        this.items=products;
        this.listener=listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_description,parent,false);
        }
        TextView productName= (TextView) convertView.findViewById(R.id.productName);
        TextView productPrice=(TextView)convertView.findViewById(R.id.productPrice);

        productName.setText(product.getName());
        productPrice.setText("$"+product.getPrice());


        return convertView;
    }



    @Override
    public void onClick(View view) {

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(listener!=null){
            listener.OnOrderDataChanged(getCount());
        }
    }
}
