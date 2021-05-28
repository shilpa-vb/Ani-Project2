package com.example.projecttwoani;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridImage_Adapter extends ArrayAdapter<GridImages_Class> {

    private Context con;
    private int layoutResId;
    private ArrayList<GridImages_Class> arrayGrid = new ArrayList<GridImages_Class>();

    public GridImage_Adapter(Context con, int layoutResId, ArrayList<GridImages_Class> arrayGrid) {
        super(con, layoutResId, arrayGrid);
        this.con = con;
        this.layoutResId = layoutResId;
        this.arrayGrid = arrayGrid;
    }

    public void setArrayGrid(ArrayList<GridImages_Class> arrayGrid){
        this.arrayGrid = arrayGrid;
        notifyDataSetChanged();
    }

    static class ViewHolder{
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        ViewHolder holder;

        if(view==null){
            LayoutInflater inflater = ((Activity)con).getLayoutInflater();
            view = inflater.inflate(layoutResId, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) view.findViewById(R.id.item_grid_image);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder)view.getTag();
        }

        GridImages_Class gImg = arrayGrid.get(position);
        Picasso.get()
                .load(gImg.getPreviewURL())
                .into(holder.img);

        return view;

    }

}
