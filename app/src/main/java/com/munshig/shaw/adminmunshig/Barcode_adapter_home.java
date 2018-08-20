package com.munshig.shaw.adminmunshig;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Barcode_adapter_home extends ArrayAdapter<String> {

    Activity context;
    List<String> barcodeList;

    public Barcode_adapter_home(@NonNull Context context, List<String> barcodeList) {
        super(context, R.layout.unlisted_items, barcodeList);
        this.context = (Activity) context;
        this.barcodeList = barcodeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listview = inflater.inflate(R.layout.unlisted_items,null, true);
        TextView barcodes_name = listview.findViewById(R.id.barcode_name);

        String item = barcodeList.get(position);

        barcodes_name.setText(item);

        return listview;
    }
}
