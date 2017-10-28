package com.darkknight.darkcard.enums;

import com.darkknight.darkcard.R;

/**
 * Created by Rooparsh on 24/10/17.
 */

public enum CreditCard {

    VISA("VISA", "^4[0-9]{6,}$", R.drawable.ic_visa),
    MASTERCARD("MASTERCARD", "^5[1-5][0-9]{5,}$", R.drawable.ic_mastercard),
    MAESTRO("MAESTRO", "^(5018|5020|5038|6304|6759|6761|6763)[0-9]{8,15}$", R.drawable.ic_mastercard_template_32),
    AMERICAN_EXPRESS("AMERICAN EXPRESS", "^3[47][0-9]{5,}$", R.drawable.ic_amex),
    DINERS("DINERS CLUB", "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$", R.drawable.ic_diners),
    DISCOVER("DISCOVER", "^6(?:011|5[0-9]{2})[0-9]{3,}$", R.drawable.ic_discover),
    JCB("JCB", "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$", R.drawable.ic_jcb),
    DEFAULT("", "", R.drawable.ic_unknown);

    private String mCardName;
    private String mRegex;
    private int mCardImage;

    /**
     * Credit card constructor
     *
     * @param cardName  name of credit card type
     * @param regex     regex pattern of card type
     * @param cardImage card type image
     */
    CreditCard(final String cardName, final String regex, final int cardImage) {
        mCardName = cardName;
        mRegex = regex;
        mCardImage = cardImage;
    }

    /**
     * Method to get CardForm object via card number
     *
     * @param cardNumber credit card number
     * @return credit card
     */
    public static CreditCard getCardByNumber(final String cardNumber) {
        CreditCard mCreditCard = null;

        for (CreditCard creditCard : values()) {
            if (cardNumber.matches(creditCard.mRegex)) {
                mCreditCard = creditCard;
            }
        }

        return mCreditCard == null
                ? DEFAULT
                : mCreditCard;
    }

    /**
     * Method to get CardForm object via card number
     *
     * @param cardName credit card type
     * @return credit card
     */
    public static CreditCard getCardByCardName(final String cardName) {
        CreditCard mCreditCard = null;

        for (CreditCard creditCard : values()) {
            if (cardName.equals(creditCard.mCardName)) {
                mCreditCard = creditCard;
            }
        }
        return mCreditCard == null
                ? DEFAULT
                : mCreditCard;
    }

    /**
     * Method to get card image
     *
     * @return drawable
     */
    public int getCardImage() {
        return mCardImage;
    }
}
