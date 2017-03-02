package me.anky.coolchineseidioms;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this, rootView);

        categoryList = new ArrayList<>(Arrays.asList(idiomCategories));
        idiomCategoryAdapter = new IdiomCategoryAdapter(getActivity(), categoryList);
        listView.setAdapter(idiomCategoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IdiomCategory idiomCategory = idiomCategoryAdapter.getItem(position);
                String selection = idiomCategory.getSelection();
                String newTitle = idiomCategory.getCategory();
                Intent intent = new Intent(getContext(), IdiomListActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, selection);
                intent.putExtra(Intent.EXTRA_TITLE, newTitle);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
