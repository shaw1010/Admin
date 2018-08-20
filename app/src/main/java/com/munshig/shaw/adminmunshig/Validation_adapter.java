package com.munshig.shaw.adminmunshig;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class Validation_adapter extends ArrayAdapter<Barcodes> {

    private Activity context;
    private List<Barcodes> barcodeList;
    Intent i;
    String kirana_name;


    public Validation_adapter(Activity context, List<Barcodes> barcodeList, Intent position) {
        super(context, R.layout.kirana_unlisted_items, (barcodeList));
        this.context = context;
        this.barcodeList = barcodeList;
        this.i = position;
    }

    private class ViewHolder {
        EditText name;
        EditText price;
        EditText packet;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.kirana_unlisted_items, null, true);

        holder = new ViewHolder();
        holder.name = listViewItem.findViewById(R.id.check_name);
        holder.price = listViewItem.findViewById(R.id.check_price);
        holder.packet = listViewItem.findViewById(R.id.check_code);

        TextView barcodes = (TextView) listViewItem.findViewById(R.id.kirana_name);

        Barcodes bar = barcodeList.get(position);

        barcodes.setText(bar.getVendor_name());
        holder.name.setText(bar.getName());
        holder.price.setText(bar.getPrice());
        holder.packet.setText(bar.getPacket_price());


        return listViewItem;
    }
}

