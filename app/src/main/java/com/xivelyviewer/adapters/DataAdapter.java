package com.xivelyviewer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.xivelyviewer.R;
import com.xivelyviewer.viewholders.DataViewHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.ButterKnife;

/**
 * TODO: document your custom view class.
 */
public class DataAdapter extends ArrayAdapter<LinkedHashMap<Object, Object>>
{
    private final Context mContext;
    private final LayoutInflater mInflater;


    public DataAdapter(final Context context)
    {
        super(context, R.layout.sample_data_adapter, new ArrayList<LinkedHashMap<Object, Object>>());
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    /**Function handling images **/
    public void setViews(int position, DataViewHolder holder){
        try
        {
            if (this.getItem(position).get("id").toString().toLowerCase().contains("hygro"))
            {
                if (Float.valueOf(this.getItem(position).get("current_value").toString()) < 50f)
                {
                    holder.getImage().getIconicFontDrawable().
                            setIconColor(getContext().getResources().getColor(R.color.md_red_200));
                    holder.getImage().setIcon("meteo-thermometer");
                }
                else if (Float.valueOf(this.getItem(position).get("current_value").toString()) >= 50f
                        && Float.valueOf(this.getItem(position).get("current_value").toString()) < 60f)
                {
                    holder.getImage().setIcon("meteo-cloud2");
                    holder.getImage().getIconicFontDrawable().
                            setIconColor(getContext().getResources().getColor(R.color.md_blue_50));
                }
                else if (Float.valueOf(this.getItem(position).get("current_value").toString()) >= 60f
                        && Float.valueOf(this.getItem(position).get("current_value").toString()) <= 70f)
                {
                    holder.getImage().setIcon("meteo-rainy");
                    holder.getImage().getIconicFontDrawable().
                            setIconColor(getContext().getResources().getColor(R.color.md_blue_300));
                }
                else if (Float.valueOf(this.getItem(position).get("current_value").toString()) > 80f)
                {
                    holder.getImage().getIconicFontDrawable().
                            setIconColor(getContext().getResources().getColor(R.color.md_blue_500));
                    holder.getImage().setIcon("meteo-rainy2");
                }
                else
                    holder.getImage().setIcon("meteo-none");


                holder.getCurrentvalue_degree().setText("%");
                holder.getMaxvalue_degree().setText("%");
                holder.getMinvalue_degree().setText("%");
            }
            else
            {
                if (Float.valueOf(this.getItem(position).get("current_value").toString()) <= 20f
                        && Float.valueOf(this.getItem(position).get("current_value").toString()) >= 10f)
                {
                    holder.getImage().getIconicFontDrawable().
                            setIconColor(getContext().getResources().getColor(R.color.md_yellow_300));
                    holder.getImage().setIcon("meteo-sunrise");
                }
                else if (Float.valueOf(this.getItem(position).get("current_value").toString()) < 10f)
                {
                    holder.getImage().getIconicFontDrawable().
                            setIconColor(getContext().getResources().getColor(R.color.material_deep_teal_200));
                    holder.getImage().setIcon("meteo-snowflake");
                }
                else if (Float.valueOf(this.getItem(position).get("current_value").toString()) > 20f
                        && Float.valueOf(this.getItem(position).get("current_value").toString()) < 30f)
                {
                    holder.getImage().setIcon("meteo-sun");
                    holder.getImage().getIconicFontDrawable().
                            setIconColor(getContext().getResources().getColor((R.color.md_yellow_500)));
                }
                else if (Float.valueOf(this.getItem(position).get("current_value").toString()) >= 30f)
                {
                    holder.getImage().getIconicFontDrawable().setIconColor(getContext()
                            .getResources().getColor(R.color.md_red_200));
                    holder.getImage().setIcon("meteo-thermometer");
                }
                else
                    holder.getImage().setIcon("meteo-none");

                holder.getCurrentvalue_degree().setText("°C");
                holder.getMaxvalue_degree().setText("°C");
                holder.getMinvalue_degree().setText("°C");
            }
            holder.getName().setText(this.getItem(position).get("id").toString());

        } catch (NumberFormatException excv)
        {
            Log.d("Error", excv.getMessage());
            holder.getImage().setIcon("meteo-none");
        }



        holder.getCurrent_value().setText(this.getItem(position).get("current_value").toString());
        holder.getMax().setText(this.getItem(position).get("max_value").toString());
        holder.getMini().setText(this.getItem(position).get("min_value").toString());
    }

    @Override public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;

        DataViewHolder holder;


        if (convertView == null)
        {
            view = mInflater.inflate(R.layout.sample_data_adapter, parent, false);
            holder = new DataViewHolder();

            ButterKnife.inject(holder, view);
            view.setTag(holder);
        }
        else
        {
            view = convertView;
            holder = (DataViewHolder) view.getTag();
        }

        setViews(position, holder);

        return view;
    }
}
