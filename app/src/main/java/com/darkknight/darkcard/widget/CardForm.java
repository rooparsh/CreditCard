package com.darkknight.darkcard.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.darkknight.darkcard.R;
import com.darkknight.darkcard.textWatcher.CreditCardTextWatcher;
import com.darkknight.darkcard.textWatcher.ExpiryDateWatcher;
import com.darkknight.darkcard.util.StringUtil;
import com.darkknight.darkcard.util.ValidateEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rooparsh on 25/10/17.
 */

//CHECKSTYLE:OFF
public class CardForm extends RelativeLayout implements View.OnClickListener, View.OnFocusChangeListener, ActionMode.Callback {

    static final int MAX_VALID_YEAR = 9980;
    private static final int TEN = 10;
    private Context mActivity;
    private AttributeSet mAttributeSet;
    private EditText etCardNumber;
    private EditText etExpiryDate;
    private EditText etOwnerName;
    private EditText etCvv;

    /**
     * Class to initialize card object
     *
     * @param activity context of calling activity
     */
    public CardForm(final Context activity) {
        super(activity);
        mActivity = activity;
        init();
    }


    /**
     * Class to initialize card
     *
     * @param activity     context of calling activity
     * @param attributeSet attribute set
     */
    public CardForm(final Context activity, final AttributeSet attributeSet) {
        super(activity, attributeSet);
        mActivity = activity;
        mAttributeSet = attributeSet;
        init();
    }


    public CardForm(final Context activity, final AttributeSet attributeSet, final int defStyle) {
        super(activity, attributeSet, defStyle);
        mActivity = activity;
        mAttributeSet = attributeSet;
        int mDefStyle = defStyle;
        init();
    }

    /**
     * Method to initialize views
     */
    private void init() {

        inflate(getContext(), R.layout.credit_card_layout, this);

        etCardNumber = (EditText) findViewById(R.id.edt_card_no);
        etExpiryDate = (EditText) findViewById(R.id.edt_exp_date);
        etOwnerName = (EditText) findViewById(R.id.edt_card_name);
        etCvv = (EditText) findViewById(R.id.edt_cvv);

        etCardNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_unknown, 0, 0, 0);
        etCardNumber.addTextChangedListener(new CreditCardTextWatcher(mActivity, etCardNumber));

        etExpiryDate.setCustomSelectionActionModeCallback(this);
        etExpiryDate.setTextIsSelectable(false);
        etExpiryDate.setLongClickable(false);
        etExpiryDate.addTextChangedListener(new ExpiryDateWatcher());
        etExpiryDate.setOnFocusChangeListener(this);
        etExpiryDate.setOnClickListener(this);

    }

    /**
     * Method to get Credit Card number
     *
     * @return credit card number without any special characters
     */
    public String getCreditCardNumber() {
        return StringUtil.getStringFromText(etCardNumber).replaceAll("-", "");
    }

    /**
     * Method to get last 4 digits of credit card number
     *
     * @return last 4 digits String
     */
    public String getLast4() {
        String cardNumber = StringUtil.getStringFromText(etCardNumber);
        return cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
    }


    /**
     * Method to validate expiry Date
     *
     * @return true if validated else false
     */
    public boolean validExpiryDate() {
        int expiryMonth = getExpiryMonth();
        int expiryYear = getFullYear();


        if (expiryMonth < 1 || expiryMonth > 12) {
            return false;
        }

        if (expiryYear < 0 || expiryYear > MAX_VALID_YEAR) {
            return false;
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy", Locale.getDefault());
        dateFormat.setLenient(false);

        Date parsedDate = null;

        try {
            parsedDate = dateFormat.parse(StringUtil.getStringFromText(etExpiryDate));
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        if (parsedDate == null) {
            return false;
        }
        Calendar mSelExpiry = Calendar.getInstance();
        mSelExpiry.setTime(parsedDate);

        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        if (expiryMonth < currentMonth) {
            return false;
        }

        if (expiryYear < currentYear) {
            return false;
        } else {
            if (expiryYear > currentYear) {
                return true;
            } else { // the card expires this year
                int readableMonth = mSelExpiry.get(Calendar.MONTH) + 1;
                return expiryMonth >= readableMonth;
            }
        }
    }

    /**
     * get Month from expiry date
     *
     * @return month int
     */
    public int getExpiryMonth() {
        return Integer.parseInt(StringUtil.getStringFromText(etExpiryDate).substring(0, 2));
    }

    /**
     * get Year from expiry date
     *
     * @return year int
     */
    public int getExpiryYear() {
        return Integer.parseInt(StringUtil.getStringFromText(etExpiryDate).substring(3, 5));
    }

    /**
     * Method to get 4 digit year
     *
     * @return 4 digit year
     */
    public int getFullYear() {
        int year = getExpiryYear();

        if (year < 100 && year >= 0) {
            Calendar now = Calendar.getInstance();
            String currentYear = String.valueOf(now.get(Calendar.YEAR));
            String prefix = currentYear.substring(0, currentYear.length() - 2);
            year = Integer.parseInt(String.format(Locale.US, "%s%02d", prefix, year));
        }
        return year;
    }

    /**
     * get card owner name
     *
     * @return name string
     */
    public String getOwnerName() {
        return StringUtil.getStringFromText(etOwnerName);
    }

    /**
     * Method to get cvv details
     *
     * @return cvv number
     */
    public String getCvv() {
        return StringUtil.getStringFromText(etCvv);
    }

    /**
     * Method to validate credit card number
     *
     * @param number credit card number
     * @return true if validated else false
     */
    public boolean validateCardNumber(final String number) {
        int s1 = 0, s2 = 0;
        final String reverse = new StringBuffer(number).reverse().toString();
        for (int i = 0; i < reverse.length(); i++) {
            final int digit = Character.digit(reverse.charAt(i), TEN);
            if (i % 2 == 0) {
                //this is for odd digits, they are 1-indexed in the algorithm
                s1 += digit;
            } else {
                //add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digit;
                if (digit >= 5) {
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % TEN == 0;
    }

    @Override
    public void onClick(final View v) {
        etExpiryDate.setSelection(StringUtil.getStringFromText(etExpiryDate).length());
    }

    @Override
    public void onFocusChange(final View v, final boolean hasFocus) {
        if (hasFocus) {
            etExpiryDate.setSelection(StringUtil.getStringFromText(etExpiryDate).length());
        }
    }

    @Override
    public boolean onCreateActionMode(final ActionMode mode, final Menu menu) {
        return false;
    }

    @Override
    public boolean onPrepareActionMode(final ActionMode mode, final Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(final ActionMode mode, final MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(final ActionMode mode) {

    }

    /**
     * Method to validate card
     *
     * @return true if validated else false
     */
    public boolean validateCard() {
        if (!ValidateEditText.checkName(etOwnerName)) {
            return false;
        }

        if (!ValidateEditText.genericEmpty(etCardNumber,
                getResources().getString(R.string.fill_card_number))) {
            return false;
        }

        if (!ValidateEditText.genericEmpty(etExpiryDate,
                getResources().getString(R.string.fill_card_expiry))) {
            return false;
        }

        if (!ValidateEditText.genericEmpty(etCvv,
                getResources().getString(R.string.fill_card_cvv))) {
            return false;
        }

        if (!validateCardNumber(getCreditCardNumber())) {

            new AlertDialog.Builder(getContext())
                    .setMessage(getResources().getString(R.string.valid_card_number))
                    .show();
            return false;
        }

        if (!validExpiryDate()) {

            new AlertDialog.Builder(getContext())
                    .setMessage(getResources().getString(R.string.valid_card_expiry))
                    .show();

            return false;
        }

        if (StringUtil.getStringFromText(etCvv).length() < 3 || StringUtil.getStringFromText(etCvv).length() > 4) {

            new AlertDialog.Builder(getContext())
                    .setMessage(getResources().getString(R.string.valid_card_cvv))
                    .show();
            return false;
        }
        return true;
    }
}
//CHECKSTYLE:ON