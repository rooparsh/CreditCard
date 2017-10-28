package com.darkknight.darkcard.util;

import android.content.Context;
import android.widget.EditText;
import com.darkknight.darkcard.R;

public final class ValidateEditText {
    private static final String REGEX_MORE_SPACE = "[ ]{2,}";
    private static final int MIN_NAME_LENGTH = 2;

    /**
     * Empty Constructor
     * not called
     */
    private ValidateEditText() {
    }

    /**
     * @param et instance of edit text
     * @return boolean
     */
    private static boolean genericEmpty(final EditText et) {
        return et.getText().toString().trim().isEmpty();
    }

    /**
     * Method to validate name field
     *
     * @param et et instance of edit text
     * @return boolean
     */
    public static boolean checkName(final EditText et) {
        String name = et.getText().toString().trim().replaceAll(REGEX_MORE_SPACE, " ");
        et.setText(StringUtil.toCamelCase(name));
        if (genericEmpty(et)) {
            String msg;
            msg = getContext(et).getString(R.string.error_name_field_empty);
            return setErrorAndRequestFoucs(et, msg);
        }

        //It takes alphabets and spaces and dots...
        if (!name.matches("^[\\p{L} .'-]+$")) {
            String msg;
            msg = getContext(et).getString(R.string.error_name_special_number_character);
            return setErrorAndRequestFoucs(et, msg);
        }


        if (name.length() < MIN_NAME_LENGTH) {
            String msg;
            msg = getContext(et).getString(R.string.error_name_two_character_long);
            return setErrorAndRequestFoucs(et, msg);
        }
        return true;
    }

    /**
     * @param et           instance of edit text
     * @param errorMessage error msg
     * @return boolean
     */
    private static boolean setErrorAndRequestFoucs(final EditText et, final String errorMessage) {
        et.setError(errorMessage);
        et.setSelection(et.getText().toString().length());
        et.setHovered(true);
        et.requestFocus();
        return false;
    }

    /**
     * @param et instance of edit text
     * @return context
     */
    private static Context getContext(final EditText et) {
        return et.getContext();
    }

    /**
     * Method to validate field is empty or not
     *
     * @param et           instance of edit text
     * @param errorMessage error message
     * @return boolean
     */
    public static boolean genericEmpty(final EditText et, final String errorMessage) {
        if (et.getText().toString().trim().isEmpty()) {
            return setErrorAndRequestFoucs(et, errorMessage);
        }
        return true;
    }
}
