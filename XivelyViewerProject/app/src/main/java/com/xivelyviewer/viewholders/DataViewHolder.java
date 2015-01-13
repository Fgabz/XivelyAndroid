package com.xivelyviewer.viewholders;

import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.thedazzler.droidicon.badges.DroidiconBadge;
import com.xivelyviewer.R;

import butterknife.InjectView;
import lombok.Data;

/**
 * Created by Fanilo on 02/01/2015.
 */

@Data
public class DataViewHolder
{
    @InjectView(R.id.card_view) CardView mCardview;
   // @InjectView(R.id.date) TextView mDate;
    @InjectView(R.id.image) DroidiconBadge image;
    @InjectView(R.id.name) TextView name;
    @InjectView(R.id.minvalue) TextView mini;
    @InjectView(R.id.maxvalue) TextView max;
    @InjectView(R.id.current_value) TextView current_value;

    @InjectView(R.id.maxvalue_degree) TextView maxvalue_degree;
    @InjectView(R.id.minvalue_degree) TextView minvalue_degree;
    @InjectView(R.id.current_value_degree) TextView currentvalue_degree;
}
