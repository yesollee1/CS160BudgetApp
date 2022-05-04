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
//    protected Double mCurrentAmount;
    protected Double mAmountSpent;
    protected int mNotifyPercent;

    public Category(String title, Frequency frequency, Double proposedAmount) {

        mTitle = title;
        mId = UUID.randomUUID();
        mDate = new Date();
        mFrequency = frequency;
        if(validateDouble(proposedAmount)){
            mProposedAmount = proposedAmount;
        }else{
            mProposedAmount = 0.0;
        }

//        mCurrentAmount = currentAmount;
        mAmountSpent = 0.0;
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
        return mProposedAmount - mAmountSpent;
    }

    public Double getAmountSpent() {
        return mAmountSpent;
    }

    public void setAmountSpent(Double amountSpent) {
        mAmountSpent = amountSpent;
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

    private boolean validateDouble(Double amount){
        if (amount == null || amount == Double.MAX_VALUE || amount == Double.MIN_VALUE) {
            return false;
        }
        return true;
    }
}
