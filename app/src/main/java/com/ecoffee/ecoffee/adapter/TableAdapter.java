package com.ecoffee.ecoffee.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ecoffee.ecoffee.R;
import com.ecoffee.ecoffee.intefrace.OnTableDataChanged;
import com.ecoffee.ecoffee.model.Product;
import com.ecoffee.ecoffee.model.Table;
import com.ecoffee.ecoffee.ui.MakeOrderCustomDialog;
import com.ecoffee.ecoffee.ui.OrderDetailsActivity;
import com.ecoffee.ecoffee.util.AppUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Vlade Ilievski on 7/22/2016.
 */
public class TableAdapter extends ArrayAdapter<Table> implements View.OnClickListener {
    private List<Table> items;
    private int layoutResource;
    private OnTableDataChanged listener;
    private MakeOrderCustomDialog.OnOrderAdded orderListener;

    public TableAdapter(Context context, int layoutResource, List<Table> items, OnTableDataChanged listener, MakeOrderCustomDialog.OnOrderAdded orderListener) {
        super(context, layoutResource, items);
        this.items = items;
        this.layoutResource = layoutResource;
        this.listener = listener;
        this.orderListener = orderListener;
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
        plusOrder.setTag("" + position);

        String tableDesc = getCountedDescription(table);// getDescription(table);
        orderDescription.setText(!tableDesc.equals("") ? tableDesc : "No orders in this table");
        name.setText(table.getName());
        plusOrder.setOnClickListener(this);

        if (table.getOrder().isPaid()) {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.paidGreen));
            plusOrder.setText("Refresh Table");
        } else {
            view.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
            plusOrder.setText("ADD ORDER");
        }

        Log.d("TableAdapter", "count=" + table.getOrder().countProductInTable());

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addOrder:
                int clickedTablePosition = Integer.valueOf((String) view.getTag());
                Table table = AppUtil.getTables().get(clickedTablePosition);
                if (!table.getOrder().isPaid()) {
                    MakeOrderCustomDialog dialog = new MakeOrderCustomDialog(getContext(), clickedTablePosition, orderListener);
                    dialog.show();
                } else {
                    final AlertDialog d = new AlertDialog.Builder(getContext())
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
                                    d.dismiss();
                                }
                            });
                        }
                    });
                    d.show();
                }
                break;
        }
    }

                private String getDescription (Table table){
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

            private String getCountedDescription (Table table){
                String desc = "";
                for (Map.Entry<String, Integer> entry : table.getOrder().countProductInTable().entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    desc += key + " x " + value + "\n";
                }
                return desc;
            }


            @Override
            public void notifyDataSetChanged () {
                super.notifyDataSetChanged();
                if (listener != null) {
                    listener.onTableDataChanged(getCount());
                }
            }
        }