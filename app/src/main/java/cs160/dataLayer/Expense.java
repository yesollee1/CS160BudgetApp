package cs160.dataLayer;

import java.util.ArrayList;
import java.util.UUID;

public class Expense extends Category {

    private ArrayList<String> mMerchants = new ArrayList<>();

    public Expense() {
        super(null, Frequency.MONTHLY, 0.0, 0.0);
    }

    public Expense(String title, Frequency frequency, Double proposedAmount) {
        super(title, frequency, proposedAmount, proposedAmount);
    }

    public ArrayList<String> getMerchants() {
        return mMerchants;
    }

    public void addMerchant(String merchant) {
        if(stringValidation(merchant)){
            mMerchants.add(merchant);
        }
    }

    public boolean containsMerchant(String merchant) {
        return mMerchants.contains(merchant);
    }

    private boolean stringValidation(String msg){
        return msg == null || msg == ""? false: true;
    }
//    this method exists in the Category class
//    public boolean spend(Double amount) {
//        if (mCurrentAmount - amount < 0) {
//            return false;
//        } else {
//            mCurrentAmount -= amount;
//            return true;
//        }
//    }
}
