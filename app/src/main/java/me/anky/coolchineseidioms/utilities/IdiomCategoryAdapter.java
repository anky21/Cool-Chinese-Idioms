package me.anky.coolchineseidioms.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.anky.coolchineseidioms.R;

/**
 * Created by Anky An on 2/03/2017.
 * anky25@gmail.com
 */

public class IdiomCategoryAdapter extends ArrayAdapter<IdiomCategory> {

    /**
     * Create a new {@link IdiomCategoryAdapter} object
     *
     * @param context         is the current context
     * @param idiomCategories is the list of {@link IdiomCategory}s to be displayed
     */
    public IdiomCategoryAdapter(Context context, List<IdiomCategory> idiomCategories) {
        super(context, 0, idiomCategories);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.category_list_item, parent, false);
        }
        // Get the {@link IdiomCategory} object located at this position in the list
        IdiomCategory currentCategory = getItem(position);

        String categoryName = currentCategory.getCategory();
        TextView categoryTv = (TextView)listItemView.findViewById(R.id.category_name_tv);
        categoryTv.setText(categoryName);

        return listItemView;
    }
}
