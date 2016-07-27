package com.ecoffee.ecoffee.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Vlade Ilievski on 7/27/2016.
 */
public class MakeOrder extends AlertDialog.Builder {
    public MakeOrder(Context context) {
        super(context);
    }

    @Override
    public AlertDialog create() {
        return super.create();
    }

    @Override
    public AlertDialog show() {
        return super.show();
    }



    @Override
    public AlertDialog.Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
        return super.setPositiveButton(text, listener);
    }
}
