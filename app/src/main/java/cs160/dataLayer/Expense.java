package cs160.dataLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Expense extends Category {

    private List<String> mMerchants = new ArrayList<>();

    public Expense() {
        super(null, Frequency.MONTHLY, 0.0, 0.0);
    }

    public Expense(String title, Frequency frequency, Double proposedAmount) {
        super(title, frequency, proposedAmount, proposedAmount);
    }

    public List<String> getMerchants() { return mMerchants; }

    public void addMerchant(String merchant) {
        if(stringValidation(merchant)){
            mMerchants.add(merchant);
        }
    }

    public boolean containsMerchant(String merchant) { return mMerchants.contains(merchant); }


    private boolean validateDouble(Double amount){
        if (amount == null || getCurrentAmount() - amount < 0 || amount == Double.MAX_VALUE || amount == Double.MIN_VALUE) {
            return false;
        }
        return true;
    }

    private boolean stringValidation(String msg){
        return msg == null || msg == ""? false: true;
    }
}
