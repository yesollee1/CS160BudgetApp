package cs160.dataLayer;

import java.util.ArrayList;
import java.util.UUID;

public class Expense extends Category {

//    private static ArrayList<Expense> mExpenseCategories = new ArrayList<>();
//    public static ArrayList<Expense> getExpenseCategories() {
//        return mExpenseCategories;
//    }

    private ArrayList<String> mMerchants;
    //TODO add support for transactions
//    private ArrayList<Transaction> mTransactions;

    public Expense() {
        super(null, Frequency.MONTHLY, 0.0, 0.0);
    }

    public Expense(String title, Frequency frequency, Double proposedAmount, Double currentAmount) {
        super(title, frequency, proposedAmount, currentAmount);
//        mExpenseCategories.add(this);
    }

//    public String getTitle() {
//        return mTitle;
//    }
//
//    public void setTitle(String title) {
//        mTitle = title;
//    }
//
//    public UUID getId() {
//        return mId;
//    }
//
//    public Double getCurrentAmount() {
//        return mCurrentAmount;
//    }

    public ArrayList<String> getMerchants() {
        return mMerchants;
    }

    public void addMerchant(String merchant) {
        mMerchants.add(merchant);
    }

//    public ArrayList<Transaction> getTransactions() {
//        return mTransactions;
//    }

    ////maybe this should be called categorize()?
//    public void addTransaction(Transaction transaction) {
//        mTransactions.add(transaction);
//    }

    //    public Double getProposedAmount() {
//        return mProposedAmount;
//    }
//
//    public void setProposedAmount(Double proposedAmount) {
//        mProposedAmount = proposedAmount;
//    }
//
//    public int getNotifyPercent() {
//        return mNotifyPercent;
//    }
//
//    public void setNotifyPercent(int notifyPercent) {
//        mNotifyPercent = notifyPercent;
//    }
//
//    public void delete() {
//        mExpenseCategories.remove(this);
//    }

    public boolean containsMerchant(String merchant) {
        return mMerchants.contains(merchant);
    }
}
