package com.darkknight.darkcard.textWatcher;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Rooparsh on 25/10/17.
 */

public class ExpiryDateWatcher implements TextWatcher {
    private boolean insertedS;

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        if (insertedS && !s.toString().contains("/")) {
            s.clear();
            insertedS = false;
        }
        if (s.length() == 1 && s.toString().charAt(0) != '1' && s.toString().charAt(0) != '0') {
            s.insert(0, "0");
        }
        if (s.length() == 2) {
            s.insert(s.length(), "/");
            insertedS = true;
        }
        if (s.length() > 3 && s.subSequence(s.toString().indexOf("/"), s.length()).length() > 5) {
            s.delete(s.length() - 1, s.length());
        }
        if (s.length() > 2 && s.subSequence(0, s.toString().indexOf("/")).length() > 2) {
            s.delete(s.toString().indexOf("/") - 1, s.toString().indexOf("/"));
        }
    }
}
