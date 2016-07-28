package com.ecoffee.ecoffee.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlade Ilievski on 7/28/2016.
 */
public class MakeOrderCustomDialog extends Dialog implements View.OnClickListener {
    Button buttonNO;
    Button buttonYES;
    Spinner spinner;
    Spinner spinner1;
    String getProducts;
    Context context;
    TextView produtsTextView;
    TextView quantityTextView;

    public MakeOrderCustomDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.make_orders);

        buttonYES = (Button) findViewById(R.id.buttonYES);
        buttonNO = (Button) findViewById(R.id.buttonNO);
        produtsTextView =(TextView)findViewById(R.id.productsTextView);
        quantityTextView=(TextView)findViewById(R.id.quantityTextView);
        buttonYES.setOnClickListener(this);
        buttonNO.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonYES:

                break;
            case R.id.buttonNO:
                dismiss();
                break;
        }
    }



//
//    spinner1=(Spinner)
//
//    findViewById(R.id.spinner1);
//
//    ArrayAdapter<String> dataAdapterNumbers = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
//    dataAdapterNumbers.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//    spinner1.setAdapter(dataAdapterNumbers);
//
//    buttonNO=(Button)
//
//    findViewById(R.id.buttonNO);




}


