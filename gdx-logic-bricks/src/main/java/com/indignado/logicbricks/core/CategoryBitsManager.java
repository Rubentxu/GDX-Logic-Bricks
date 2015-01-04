package com.indignado.logicbricks.core;


import com.badlogic.gdx.utils.ObjectMap;
import com.indignado.logicbricks.utils.Log;

/**
 * copy Sioncore class
 */
public class CategoryBitsManager {
    protected String tag = this.getClass().getSimpleName();
    private ObjectMap<String, Short> categoryBits;
    private ObjectMap<Short, String> categoryNames;
    private int nextCategory;


    public CategoryBitsManager() {
        Log.debug(tag, "Initialising");
        categoryBits = new ObjectMap<String, Short>();
        categoryNames = new ObjectMap<Short, String>();
        nextCategory = 0;

    }


    public short getCategoryBits(String name) {
        if (name.length() == 0) {
            return 0;
        }
        Short category = categoryBits.get(name);

        if (category == null) {
            if (nextCategory >= 16) {
                Log.error(tag, "maximum number of collision categories reached");
                return 0;
            } else {
                short newCategory = (short) (1 << (nextCategory++));
                categoryBits.put(name, newCategory);
                categoryNames.put(newCategory, name);
                Log.info(tag, "registering category %s => %d", name, newCategory);
                return newCategory;
            }
        }
        return category;

    }


    public String getCategoryName(short category) {
        if (category == 0) {
            return "";
        }
        String name = categoryNames.get(category);

        if (name == null) {
            Log.error(tag, "category for bits %d does not exist", category);
            return "";
        }
        return name;

    }

}