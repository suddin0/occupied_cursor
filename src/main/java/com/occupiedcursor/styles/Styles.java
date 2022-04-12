package com.occupiedcursor.styles;

import com.occupiedcursor.helper.Pair;

import java.util.Vector;

/**
 * This si a style class which is intended to be used to store styles for the javaFX elements
 */
public class Styles {
    Vector<Pair> styleList = null;

    public Styles() {
        styleList = new Vector<>();
    }

    /**
     * Adds a new uniq stylesheet element
     * If the key is already added then the whole procedure of adding will be ignored.
     * @param key the name of the parameter (ex : "background", "height", "margin")
     * @param value The value of the parameter (ex : "20px" , "top")
     */
    public void add(String key, String value) {
        if(key == null || value == null || key.isEmpty() || key.isBlank()) {
            System.err.println("[!] Warning : null was provided for 'key' or 'value'.");
            return;
        } else if(key.isEmpty() || key.isBlank()) {
            System.err.println("[!] Warning : Tried to add a blank key as Style : [" + key + " : " + value + "]");
            return;
        }
        if(exist(key)) {
            return;
        }
        styleList.add(new Pair<String, String>(key, value));
    }

    /**
     * Remove an existing key value pair
     * Of the key doesn't exists then nothing will be done
     * @param key the name of the key which corresponds to the key value pair you wish to remove
     */
    public void remove(String key) {
        for (Pair pair : styleList) {
            if (pair.getKey() == key) {
                styleList.remove(pair);
                break;
            }
        }
    }

    /**
     * Updates an existing key value pair
     * This functions searches for an existing key using the key parameter provided and if exists it will update the
     * value of the key, else it will ignore the procedure.
     * @param key the key you wish to update
     * @param value the new value for the existing keu
     */
    public void update(String key, String value) {

        for (Pair pair : styleList) {
            if (pair.getKey() == key) {
                pair.setValue(value);
                break;
            }
        }
    }

    /**
     * Checks if the key already exists in the list
     * @param key
     * @return true if exists else returns falese
     */
    public Boolean exist(String key) {
        if (key == null || key.isEmpty() || key.isBlank()) {
            return false;
        }

        for (Pair pair : styleList) {
            if (pair.getKey() == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String strStyles = "";
        if(styleList.size() == 0) return strStyles;

        for(Pair pair : styleList) {
            strStyles += pair.toString() + ";";
        }
        return strStyles;
    }
}
