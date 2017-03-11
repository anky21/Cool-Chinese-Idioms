package me.anky.coolchineseidioms.utilities;

/**
 * Created by Anky An on 2/03/2017.
 * anky25@gmail.com
 */

public class IdiomCategory {
    /**
     * Idiom category data
     */
    private String mCategory;
    private String mSelection;

    /*
    * Construct a new {@link IdiomCategory} object
    *
    * @param category   is the name of the category
    * @param selection  is the selection used to query a database
    * */
    public IdiomCategory(String category, String selection){
        mCategory = category;
        mSelection = selection;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getSelection() {
        return mSelection;
    }
}
