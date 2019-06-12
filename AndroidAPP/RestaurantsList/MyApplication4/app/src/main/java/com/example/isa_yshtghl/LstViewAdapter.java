package com.example.isa_yshtghl;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class LstViewAdapter extends ArrayAdapter<String> {
    int groupid;
    String[] item_list;
    ArrayList<String> desc;
    Context context;
    public LstViewAdapter(Context context, int vg, int id, ArrayList<String> item_list){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list= item_list.toArray(new String[0]);

    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView textview;
        public Button button;

    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        View rowView = convertView;
        // Inflate the list_item.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(R.layout.restaurantlistviewitems, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textview= (TextView) rowView.findViewById(R.id.txt);
            viewHolder.button= (Button) rowView.findViewById(R.id.bt);
            rowView.setTag(viewHolder);
            if(viewHolder.textview==null){
            }

        }

        // Set text to each TextView of ListView item
        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.textview.setText(item_list[position]);
        holder.button.setText("Call");
        holder.button.setTag(item_list[position]);
        return rowView;
    }

}