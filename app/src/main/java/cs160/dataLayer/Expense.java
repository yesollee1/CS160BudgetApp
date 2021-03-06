package cs160.dataLayer;

import java.util.ArrayList;
import java.util.UUID;

public class Expense extends Category {

    //TODO: Implement move money feature

    private ArrayList<String> mMerchants = new ArrayList<>();

    public Expense() {
        super(null, Frequency.MONTHLY, 0.0, 0.0);
    }

    public Expense(String title, Frequency frequency, Double proposedAmount, Double currentAmount) {
        super(title, frequency, proposedAmount, currentAmount);
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
}
