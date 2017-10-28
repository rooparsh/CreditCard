package com.darkknight.darkcard.textWatcher;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.darkknight.darkcard.enums.CreditCard;

/**
 * Created by Rooparsh on 24/10/17.
 */

public class CreditCardTextWatcher implements TextWatcher {
    private static final char SPACE = '-';
    private Context mActivity;
    private EditText mEtCardNumber;
    private CreditCard mCreditCard;

    /**
     * @param activity Context of activity
     * @param editText EditText
     */
    public CreditCardTextWatcher(final Context activity, final EditText editText) {
        this(activity);
        this.mEtCardNumber = editText;

    }

    /**
     * @param activity context of Activity
     */
    private CreditCardTextWatcher(final Context activity) {
        mActivity = activity;
    }

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

    }

    @Override
    public void afterTextChanged(final Editable editable) {
        int pos;

        String cardNumber = editable.toString().replaceAll("[^0-9]", "");

        mCreditCard = CreditCard.getCardByNumber(cardNumber);
        applyCardImage();

        if (editable.length() > 0 && (editable.length() % 5) == 0) {
            final char c = editable.charAt(editable.length() - 1);
            if (SPACE == c) {
                editable.delete(editable.length() - 1, editable.length());
            }
        }

        // Insert char where needed.
        pos = 4;
        while (true) {
            if (pos >= editable.length()) {
                break;
            }
            final char c = editable.charAt(pos);
            // Only if its a digit where there should be a SPACE we insert a SPACE
            if ("0123456789".indexOf(c) >= 0) {
                editable.insert(pos, "" + SPACE);
            }
            pos += 5;
        }
    }


    /**
     * method to set credit card image
     */
    private void applyCardImage() {
        mEtCardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(mCreditCard.getCardImage(), 0, 0, 0);
    }
}
