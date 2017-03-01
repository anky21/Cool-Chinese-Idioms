package me.anky.coolchineseidioms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anky.coolchineseidioms.IdiomCollectionContract.IdiomCollectionEntry;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    private IdiomCategoryAdapter idiomCategoryAdapter;
    private ArrayList<IdiomCategory> categoryList;

    @BindView(R.id.category_listview)
    ListView listView;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    IdiomCategory[] idiomCategories = {
            new IdiomCategory("Containing Numbers", IdiomCollectionEntry.COLUMN_CONTAIN_NUMBERS + " =1"),
            new IdiomCategory("Containing Animals", IdiomCollectionEntry.COLUMN_CONTAIN_ANIMALS + " =1"),
            new IdiomCategory("About Seasons", IdiomCollectionEntry.COLUMN_SEASONS + " =1")
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, rootView);

        categoryList = new ArrayList<>(Arrays.asList(idiomCategories));
        idiomCategoryAdapter = new IdiomCategoryAdapter(getActivity(), categoryList);
        listView.setAdapter(idiomCategoryAdapter);

        return rootView;
    }

}
