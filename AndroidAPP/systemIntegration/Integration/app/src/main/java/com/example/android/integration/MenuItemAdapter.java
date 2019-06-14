package com.example.android.integration;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.Integration.R;

import java.util.ArrayList;

public class MenuItemAdapter extends ArrayAdapter<item> {
    int groupid;
    item[] item_list;
    ArrayList<item> desc;
    Context context;
    public MenuItemAdapter(Context context, int vg, int id, ArrayList<item> item_list){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list= item_list.toArray(new item[0]);

    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder
    {
        public TextView name;
        public TextView price;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        View rowView = convertView;
        // Inflate the list_item.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(R.layout.name_price_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name= (TextView) rowView.findViewById(R.id.item_name);
            viewHolder.price= (TextView) rowView.findViewById(R.id.item_price);
            rowView.setTag(viewHolder);
            if(viewHolder.name==null)
            {}

        }
        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.name.setText(item_list[position].name);
        holder.price.setText(Float.toString(item_list[position].price));
        return rowView;
    }

}