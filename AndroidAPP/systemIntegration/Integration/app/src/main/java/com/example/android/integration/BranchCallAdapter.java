package com.example.android.integration;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.Integration.R;

import java.util.ArrayList;

public class BranchCallAdapter extends ArrayAdapter<Branch> {
    int groupid;
    Branch[] item_list;
    ArrayList<String> desc;
    Context context;
    public BranchCallAdapter(Context context, int vg, int id, ArrayList<Branch> item_list){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list= item_list.toArray(new Branch[0]);

    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHold {
        public TextView textview;
        public ImageView call_button;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        View rowView = convertView;
        // Inflate the list_item.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(R.layout.branch_call, parent, false);
            ViewHold viewHold = new ViewHold();
            viewHold.textview= (TextView) rowView.findViewById(R.id.branch_name);
            viewHold.call_button = rowView.findViewById(R.id.call_button);
            rowView.setTag(viewHold);
            if(viewHold.textview==null)
            {}

        }

        // Set text to each TextView of ListView item
       ViewHold holder = (ViewHold) rowView.getTag();
        holder.textview.setText(item_list[position].address);
        holder.call_button.setTag(item_list[position].id);
        return rowView;
    }

}
