package cs160.dataLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class Category {
//    private static UUID sNextId;
    protected String mTitle;
    protected UUID mId;
    protected Date mDate;
    protected Frequency mFrequency;
    protected Double mProposedAmount;
    protected Double mCurrentAmount;
    protected int mNotifyPercent;

    public Category(String title, Frequency frequency, Double proposedAmount, Double currentAmount) {
        mTitle = title;
        mId = UUID.randomUUID();
        mDate = new Date();
        mFrequency = frequency;
        mProposedAmount = proposedAmount;
        mCurrentAmount = currentAmount;
    }

    public Category(){
        mId = UUID.randomUUID();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public Frequency getFrequency() {
        return mFrequency;
    }

    public void setFrequency(Frequency frequency) {
        mFrequency = frequency;
    }

    public Double getCurrentAmount() {
        return mCurrentAmount;
    }

    public Double getProposedAmount() {
        return mProposedAmount;
    }

    public void setProposedAmount(Double proposedAmount) {
        mProposedAmount = proposedAmount;
    }

    public int getNotifyPercent() {
        return mNotifyPercent;
    }

    public void setNotifyPercent(int notifyPercent) {
        mNotifyPercent = notifyPercent;
    }

    //TODO: Implement delete for Expenses, Goals, and Transactions
//    public abstract void delete();
}
