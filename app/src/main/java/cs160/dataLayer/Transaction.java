package cs160.dataLayer;

import java.util.Date;
import java.util.UUID;

import cs160.UILayer.*;

public class Transaction {
    protected UUID mId;
    private String mMerchant;
    private Double mAmount;
    private Date mDate;
    private String mExpenseName;
    private String mNotes;

    public Transaction() {
        mId = UUID.randomUUID();
        mMerchant = null;
        mDate = new Date();
        mAmount = 0.0;
        mExpenseName = null;
        mNotes = null;
    }

    public Transaction(String title, Double amount) {
        mMerchant = title;
        mId = UUID.randomUUID();
        mAmount = amount;
        mDate = new Date();
        mExpenseName = null;
        mNotes = null;
    }

    public UUID getId() {
        return mId;
    }

    public String getMerchant() {
        return mMerchant;
    }

    public void categorize(String merchant) { // setMerchant()
        mMerchant = merchant;
    }

    public Double getAmount() {
        return mAmount;
    }

    public void setAmount(Double amount) {
        mAmount = amount;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getExpenseName() {
        return mExpenseName;
    }

    public void setExpenseName(String expenseName) {
        mExpenseName = expenseName;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }
}
