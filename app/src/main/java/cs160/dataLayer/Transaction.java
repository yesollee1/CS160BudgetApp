package cs160.dataLayer;

import android.content.Context;

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
    private boolean mIsCategorized;

    public Transaction() {
        mId = UUID.randomUUID();
        mMerchant = null;
        mDate = new Date();
        mAmount = 0.0;
        mExpenseName = null;
        mNotes = null;
    }

    public Transaction(String title, Double amount, String expenseName, Date date) {
        mMerchant = title;
        mId = UUID.randomUUID();
        if(doubleValidation(amount)){
            mAmount = amount;
        }else{
            mAmount = 0.0;
        }
        mDate = date;
        mExpenseName = expenseName;
        mNotes = null;
    }

    public String getTitle() {return mMerchant;}

    public UUID getId() {
        return mId;
    }

    public String getMerchant() {
        return mMerchant;
    }

    public void setMerchant(String merchant) {
        // Has tests
        if(stringValidation(merchant)){
            mMerchant = merchant;
        }
    }

//    public void categorize(String merchant) { // setMerchant()
//        mMerchant = merchant;
//    }

    public boolean spendFrom(Context context, String expenseName) {
        mExpenseName = expenseName;
        Expense expense = ExpenseLab.get(context).getExpenseByName(expenseName);
//        expense.addTransaction(); // may be unnecessary
        return expense.spend(mAmount);
    }

    public Double getAmount() {
        return mAmount;
    }

    public void setAmount(Double amount) {
        // Has tests
        if(doubleValidation(amount)){
            mAmount = amount;
        }
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        if(date!=null){
            mDate = date;
        }
    }

    public String getExpenseName() {
        return mExpenseName;
    }

    public void setExpenseName(String expenseName) {
        // Has tests
        if(stringValidation(expenseName)){
            mExpenseName = expenseName;
        }
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        if(stringValidation(notes)){
            mNotes = notes;
        }
    }

    public void setCategorized(boolean categorized) {
        mIsCategorized = categorized;
    }

    public boolean isCategorized() {
        return mIsCategorized;
    }

    private boolean stringValidation(String msg){
        return msg == null || msg == ""? false: true;
    }

    private boolean doubleValidation(Double amount){
        if(amount == null || amount < 0 || amount == Double.MIN_VALUE || amount == Double.MAX_VALUE){
            return false;
        }
        return true;
    }
}
