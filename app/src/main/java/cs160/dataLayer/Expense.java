package cs160.dataLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Expense extends Category {

    private List<String> mMerchants = new ArrayList<>();

    public Expense() {
        super(null, Frequency.MONTHLY, 0.0);
    }

    public Expense(String title, Frequency frequency, Double proposedAmount) {
        super(title, frequency, proposedAmount);
    }

    public List<String> getMerchants() { return mMerchants; }

    public void addMerchant(String merchant) { mMerchants.add(merchant); }

    public boolean containsMerchant(String merchant) { return mMerchants.contains(merchant); }

    public boolean spend(Double amount) {
        if(validateDouble(amount)){
            setAmountSpent(getAmountSpent() + amount);
            return true;
        }
        return false;
    }

    private boolean validateDouble(Double amount){
        if (amount == null || getCurrentAmount() - amount < 0 || amount == Double.MAX_VALUE || amount == Double.MIN_VALUE) {
            return false;
        }
        return true;
    }
}
