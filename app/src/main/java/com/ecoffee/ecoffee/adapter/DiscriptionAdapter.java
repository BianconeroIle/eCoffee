package com.ecoffee.ecoffee.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.model.Product;

/**
 * Created by Vlade Ilievski on 7/25/2016.
 */
public class DiscriptionAdapter extends ArrayAdapter<Product> implements View.OnClickListener{


    public DiscriptionAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void onClick(View view) {

    }
}
