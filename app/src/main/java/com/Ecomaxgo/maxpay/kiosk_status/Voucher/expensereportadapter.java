package com.Ecomaxgo.maxpay.kiosk_status.Voucher;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Ecomaxgo.maxpay.kiosk_status.R;

import java.util.ArrayList;

public class expensereportadapter extends BaseAdapter{
Activity c;
ArrayList a1,a2,a3,a4,a5;
public expensereportadapter(Activity c,ArrayList a1,ArrayList a2,ArrayList a3,ArrayList a4,ArrayList a5)
{
    this.c=c;
    this.a1=a1;
    this.a2=a2;
    this.a3=a3;
    this.a4=a4;
    this.a5=a5;

}
    @Override
    public int getCount() {
        return a1.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater l=c.getLayoutInflater();
        View v=l.inflate(R.layout.activity_expensereportadapter,parent,false);
        ImageView imageView=v.findViewById(R.id.image);
        TextView date=v.findViewById(R.id.helloText);
        TextView amt=v.findViewById(R.id.amt);
        TextView status=v.findViewById(R.id.status);
        TextView desc=v.findViewById(R.id.desc);
        TextView message=v.findViewById(R.id.message);

        date.setText("Entry Date "+a1.get(position).toString());
        amt.setText("Amt. "+a2.get(position).toString());
        status.setText(a3.get(position).toString());
        desc.setText(a4.get(position).toString());
        message.setText(a5.get(position).toString());

        if (a3.get(position).toString().equalsIgnoreCase("pending"))
        {
            imageView.setImageDrawable(c.getResources().getDrawable(R.drawable.pending));
        }
        else if (a3.get(position).toString().equalsIgnoreCase("rejected"))
        {
            imageView.setImageDrawable(c.getResources().getDrawable(R.drawable.cross));
        }
        else {
            imageView.setImageDrawable(c.getResources().getDrawable(R.drawable.tick));
        }


        return v;
    }
}