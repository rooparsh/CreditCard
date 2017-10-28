package com.darkknight.darkcard.util;

import android.widget.EditText;

public final class StringUtil {

    /**
     * Empty Constructor
     * not called
     */
    private StringUtil() {
    }

    /**
     * Method to convert string into camel case string
     *
     * @param inputString string value that need to convert into camel case
     * @return converted camel cased string
     */
    static String toCamelCase(final String inputString) {
        String result = "";
        if (inputString == null || inputString.isEmpty()) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            char previousChar = inputString.charAt(i - 1);
            if (previousChar == ' ') {
                char currentCharToUpperCase = Character.toUpperCase(currentChar);
                result = result + currentCharToUpperCase;
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
        }
        return result;
    }

    /**
     * Method to get trimmed string from editText
     *
     * @param editText editText whose text is needed
     * @return trimmed string from editText
     */
    public static String getStringFromText(final EditText editText) {
        return editText.getText().toString().trim();
    }
}
