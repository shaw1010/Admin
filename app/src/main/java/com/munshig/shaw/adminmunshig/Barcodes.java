package com.munshig.shaw.adminmunshig;

import android.os.Parcel;
import android.os.Parcelable;

public class Barcodes implements Parcelable{
    String barcode;
    String name;
    String packet_price;
    String price;
    String phone;
    String vendor_name;
    Boolean selected_name;
    Boolean selected_price;
    Boolean selected_packet;

    public Barcodes(String Barcode, String Name, String Price, String Packet_Price) {
        barcode = Barcode;
        name = Name;
        price = Price;
        packet_price = Packet_Price;
    }

    public Barcodes() {

    }


    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // TODO Auto-generated method stub
        out.writeString(barcode);
        out.writeString(name);
        out.writeString(packet_price);
        out.writeString(price);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Barcodes> CREATOR
            = new Parcelable.Creator<Barcodes>() {
        public Barcodes createFromParcel(Parcel in) {
            return new Barcodes(in);
        }

        public Barcodes[] newArray(int size) {
            return new Barcodes[size];
        }
    };

    private Barcodes(Parcel in) {
        barcode = in.readString();
        name = in.readString();
        price = in.readString();
        packet_price = in.readString();
    }

    public Boolean getSelected_name() {
        return selected_name;
    }

    public void setSelected_name(Boolean selected_name) {
        this.selected_name = selected_name;
    }

    public Boolean getSelected_price() {
        return selected_price;
    }

    public void setSelected_price(Boolean selected_price) {
        this.selected_price = selected_price;
    }

    public Boolean getSelected_packet() {
        return selected_packet;
    }

    public void setSelected_packet(Boolean selected_packet) {
        this.selected_packet = selected_packet;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPacket_price() {
        return packet_price;
    }

    public void setPacket_price(String packet_price) {
        this.packet_price = packet_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public static Creator<Barcodes> getCREATOR() {
        return CREATOR;
    }
}
